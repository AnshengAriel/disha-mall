package com.ariel.disha.mall.config.sql;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.init.InjectBeans;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.jdbc.exceptions.NotUpdatable;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ariel
 * @apiNote CommonMapper
 * @serial 这个文件不能放到mapper目录，防止它被加载
 */
public interface CommonMapper<E> extends BaseMapper<E> {

    // 注入一个懒加载bean
    Supplier<SqlSessionFactory> BEAN_FACTORY = InjectBeans.inject(SqlSessionFactory.class);

    default WrapperBuilder<E> wrapper() {
        return new WrapperBuilder<>(this);
    }

    class WrapperBuilder<E> {

        private final BaseMapper<E> baseMapper;

        /**
         * 默认校验字段值，空语句被省略
         */
        private boolean unCheck;
        /**
         * 允许空条件
         */
        private boolean allowEmptyCondition;

        private final RuntimeException conditionEmptyException = new UnsupportedOperationException("The condition of where is empty.");

        private final List<Consumer<AbstractWrapper<E, String, ?>>> queryOps = new LinkedList<>();

        private final List<Consumer<UpdateWrapper<E>>> updateOps = new LinkedList<>();

        private final List<String> selectColumns = new LinkedList<>();

        private final Map<String, Number> incOps = new HashMap<>();

        private final Map<String, Number> maxLimits = new HashMap<>();

        private final Map<String, Number> minLimits = new HashMap<>();

        public WrapperBuilder(BaseMapper<E> baseMapper) {
            this.baseMapper = baseMapper;
        }

        private boolean isAllowEmptyCondition() {
            return allowEmptyCondition;
        }

        private void setAllowEmptyCondition(boolean allowEmptyCondition) {
            this.allowEmptyCondition = allowEmptyCondition;
        }

        private boolean isUnCheck() {
            return unCheck;
        }

        private void setUnCheck(boolean unCheck) {
            this.unCheck = unCheck;
        }

        public WrapperBuilder<E> configForbidCheck() {
            setUnCheck(true);
            return this;
        }

        public WrapperBuilder<E> configAllowEmptyCondition() {
            setAllowEmptyCondition(true);
            return this;
        }

        public E queryOne() {
            return baseMapper.selectOne(buildQueryWrapper());
        }

        public E queryOneOrElseThrow() {
            E e = queryOne();
            if (e == null) {
                throw new RuntimeException("Query data is null");
            }
            return e;
        }

        public List<E> queryList() {
            return baseMapper.selectList(buildQueryWrapper());
        }

        public IPage<E> queryPage(IPage<E> page) {
            return baseMapper.selectPage(page, buildQueryWrapper());
        }

        public long queryCount() {
            return baseMapper.selectCount(buildQueryWrapper());
        }

        public boolean exists() {
            return baseMapper.selectCount(buildQueryWrapper()) > NumberConst.LONG_0;
        }

        public QueryWrapper<E> buildQueryWrapper() {
            QueryWrapper<E> wrapper = new QueryWrapper<>();
            wrapper.select(selectColumns.toArray(new String[0]));
            queryOps.forEach(op -> op.accept(wrapper));
            if (!isAllowEmptyCondition() && wrapper.isEmptyOfWhere()) {
                throw conditionEmptyException;
            }
            return wrapper;
        }

        public int insertBatch(List<E> list) {
            SqlSessionFactory sqlSessionFactory = BEAN_FACTORY.get();
            SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            try {
                for (E e : list) {
                    baseMapper.insert(e);
                }
                session.commit();
            } catch (Exception e) {
                session.rollback();
            } finally {
                session.close();
            }
            return list.size();
        }

        public int update() {
            UpdateWrapper<E> wrapper = new UpdateWrapper<>();
            queryOps.forEach(op -> op.accept(wrapper));
            if (wrapper.isEmptyOfWhere()) {
                throw conditionEmptyException;
            }
            updateOps.forEach(op -> op.accept(wrapper));
            return baseMapper.update(null, wrapper);
        }

        public int updateByCas() {
            int updated = 0;
            QueryWrapper<E> queryWrapper = buildQueryWrapper();
            incOps.forEach((column, val) -> queryWrapper.select(column));
            queryWrapper.last("for update");
            while (true) {
                E e = baseMapper.selectOne(queryWrapper);
                if (e == null) {
                    return updated;
                }
                // 转化对象成map, 同时转化为带下划线名称
                JSONObject jsonObject = JSONUtil.parseObj(e);
                HashMap<String, Number> data = new HashMap<>(jsonObject.size());
                jsonObject.forEach((k, v) -> data.put(StrUtil.toUnderlineCase(k), (Number) v));
                // 校验数据库自增列不得为null, 累加值不得为null
                for (Map.Entry<String, Number> entry : incOps.entrySet()) {
                    if (data.get(entry.getKey()) == null || entry.getValue() == null) {
                        return updated;
                    }
                }
                // 校验结果的上界, 比如让余额高于100时更新失败, 对于null值列直接退出
                for (Map.Entry<String, Number> entry : maxLimits.entrySet()) {
                    Number incVal = incOps.get(entry.getKey());
                    Number num = data.get(entry.getKey());
                    if (num.doubleValue() + incVal.doubleValue() > entry.getValue().doubleValue()) {
                        return updated;
                    }
                }
                // 校验结果的下界, 比如让余额低于0时更新失败, 对于null值列直接退出
                for (Map.Entry<String, Number> entry : minLimits.entrySet()) {
                    Number incVal = incOps.get(entry.getKey());
                    Number num = data.get(entry.getKey());
                    if (num.doubleValue() + incVal.doubleValue() < entry.getValue().doubleValue()) {
                        return updated;
                    }
                }
                UpdateWrapper<E> wrapper = new UpdateWrapper<>();
                updateOps.forEach(op -> op.accept(wrapper));
                queryOps.forEach(op -> op.accept(wrapper));
                incOps.forEach((column, val) -> wrapper.eq(column, data.get(column)));
                if ((updated = baseMapper.update(null, wrapper)) > 0) {
                    return updated;
                }
            }
        }

        public int updateOrThrow() {
            int updated = update();
            if (updated == 0) {
                throw new RuntimeException(new NotUpdatable("Updated row count is 0"));
            }
            return updated;
        }

        public int delete() {
            QueryWrapper<E> wrapper = new QueryWrapper<>();
            queryOps.forEach(op -> op.accept(wrapper));
            if (wrapper.isEmptyOfWhere()) {
                throw conditionEmptyException;
            }
            return baseMapper.delete(wrapper);
        }

        public boolean valid(Object val) {
            if (val == null) {
                return false;
            }
            if (val instanceof String) {
                return !((String) val).isBlank();
            }
            if (val instanceof Collection<?>) {
                return !((Collection<?>) val).isEmpty();
            }
            if (val instanceof Object[]) {
                return ((Object[]) val).length > 0;
            }
            return true;
        }

        public WrapperBuilder<E> select(String val) {
            if (isUnCheck() || valid(val)) {
                selectColumns.add(val);
            }
            return this;
        }

        public WrapperBuilder<E> set(String column, Object val) {
            if (isUnCheck() || valid(val)) {
                updateOps.add(wrapper -> wrapper.set(column, val));
            }
            return this;
        }

        public WrapperBuilder<E> inc(String column, Number val) {
            if (isUnCheck() || valid(val)) {
                final int i = val.intValue();
                if (i == 0) {
                    return this;
                }
                updateOps.add(wrapper -> wrapper.setSql(true, String.format("%s = %s %s %s", column, column, i < 0 ? "" : "+", val)));
                incOps.put(column, val);
            }
            return this;
        }

        /**
         * 自增减值结果的上限，超过上限会更新失败
         * @param column 自增减字段
         * @param val 上界
         * @return this
         */
        public WrapperBuilder<E> max(String column, Number val) {
            if (isUnCheck() || valid(val)) {
                maxLimits.put(column, val);
            }
            return this;
        }

        /**
         * 自增减值结果的下限，低于上限会更新失败
         * @param column 自增减字段
         * @param val 下界
         * @return this
         */
        public WrapperBuilder<E> min(String column, Number val) {
            if (isUnCheck() || valid(val)) {
                minLimits.put(column, val);
            }
            return this;
        }

        public WrapperBuilder<E> eq(String column, Object val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.eq(column, val));
            }
            return this;
        }

        public WrapperBuilder<E> ne(String column, Object val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.ne(column, val));
            }
            return this;
        }

        public WrapperBuilder<E> in(String column, Object[] vals) {
            if (isUnCheck() || valid(vals)) {
                if (vals.length == 1) {
                    queryOps.add(wrapper -> wrapper.eq(column, vals[0]));
                }else {
                    queryOps.add(wrapper -> wrapper.in(column, vals));
                }
            }
            return this;
        }

        public WrapperBuilder<E> nin(String column, Object[] vals) {
            if (isUnCheck() || valid(vals)) {
                if (vals.length == 1) {
                    queryOps.add(wrapper -> wrapper.ne(column, vals[0]));
                }else {
                    queryOps.add(wrapper -> wrapper.notIn(column, vals));
                }
            }
            return this;
        }

        public WrapperBuilder<E> like(String column, String val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.like(column, val));
            }
            return this;
        }

        public WrapperBuilder<E> orderDescBy(String val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.orderByDesc(val));
            }
            return this;
        }

        public WrapperBuilder<E> orderAscBy(String val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.orderByAsc(val));
            }
            return this;
        }

        public WrapperBuilder<E> limit(Long val) {
            if (isUnCheck() || valid(val) && val > 0) {
                queryOps.add(wrapper -> wrapper.last("limit " + val));
            }
            return this;
        }

        public WrapperBuilder<E> skip(Long val) {
            if (isUnCheck() || valid(val) && val > 0) {
                queryOps.add(wrapper -> wrapper.last("skip " + val));
            }
            return this;
        }

        public WrapperBuilder<E> gte(String column, Number val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.ge(column, val));
            }
            return this;
        }

        public WrapperBuilder<E> lte(String column, Number val) {
            if (isUnCheck() || valid(val)) {
                queryOps.add(wrapper -> wrapper.le(column, val));
            }
            return this;
        }

        // 业务
        public WrapperBuilder<E> eqAuthorId(Object val) {
            return eq("author_id", val);
        }

        public WrapperBuilder<E> eqId(Object val) {
            return eq("id", val);
        }

        public WrapperBuilder<E> eqIdx(Object val) {
            return eq("idx", val);
        }

        public WrapperBuilder<E> eqBlogId(Object val) {
            return eq("blog_id", val);
        }

        public WrapperBuilder<E> eqBulletCount(Long val) {
            return eq("bullet_count", val);
        }

        public WrapperBuilder<E> eqPlayCount(Long val) {
            return eq("play_count", val);
        }

        public WrapperBuilder<E> eqCoinCount(Long val) {
            return eq("coin_count", val);
        }

        public WrapperBuilder<E> eqCurrentCoin(Long val) {
            return eq("current_coin", val);
        }

        public WrapperBuilder<E> eqFavoriteCount(Long val) {
            return eq("favorite_count", val);
        }

        public WrapperBuilder<E> eqForwardCount(Long val) {
            return eq("forward_count", val);
        }

        public WrapperBuilder<E> eqThumbsUpCount(Long val) {
            return eq("thumbs_up_count", val);
        }

        public WrapperBuilder<E> eqReplyId(Integer val) {
            return eq("reply_id", val);
        }

        public WrapperBuilder<E> eqRootId(Integer val) {
            return eq("root_id", val);
        }

        public WrapperBuilder<E> eqRoleId(Integer val) {
            return eq("role_id", val);
        }

        public WrapperBuilder<E> eqRecommended(Boolean val) {
            return eq("recommended", val);
        }

        public WrapperBuilder<E> eqReceiverId(Integer val) {
            return eq("receiver_id", val);
        }

        public WrapperBuilder<E> setDelType(Integer val) {
            return set("del_type", val);
        }

        public WrapperBuilder<E> likeTitle(String val) {
            return like("title", val);
        }

        public WrapperBuilder<E> likeNickName(String val) {
            return like("nick_name", val);
        }

        public WrapperBuilder<E> likeName(String val) {
            return like("name", val);
        }

        public WrapperBuilder<E> eqStatus(Object val) {
            return eq("status", val);
        }

        public WrapperBuilder<E> eqPhone(Object val) {
            return eq("phone", val);
        }

        public WrapperBuilder<E> eqCategoryId(Object val) {
            return eq("category_id", val);
        }

        public WrapperBuilder<E> eqCode(String val) {
            return eq("code", val);
        }

        public WrapperBuilder<E> orderDescByCreateTime() {
            return orderDescBy("create_time");
        }

        public WrapperBuilder<E> orderDescByPlayCount() {
            return orderDescBy("play_count");
        }

        public WrapperBuilder<E> orderDescByWeight() {
            return orderDescBy("weight");
        }

        public WrapperBuilder<E> orderAscByWeight() {
            return orderAscBy("weight");
        }

        public WrapperBuilder<E> eqDelType(Integer val) {
            return eq("del_type", val);
        }

        public WrapperBuilder<E> eqDate(String val) {
            return eq("date", val);
        }

        public WrapperBuilder<E> eqBlogType(Integer val) {
            return eq("blog_type", val);
        }

        public WrapperBuilder<E> eqCategoryType(Integer val) {
            return eq("category_type", val);
        }

        public WrapperBuilder<E> eqPBlogId(String val) {
            return eq("p_blog_id", val);
        }

        public WrapperBuilder<E> eqType(Integer val) {
            return eq("type", val);
        }

        public WrapperBuilder<E> eqTop(Integer val) {
            return eq("top", val);
        }

        public WrapperBuilder<E> inId(Object[] vals) {
            return in("id", vals);
        }

        public WrapperBuilder<E> setSort(Integer val) {
            return set("sort", val);
        }

        public WrapperBuilder<E> orderDescBySort() {
            return orderDescBy("sort");
        }

        public WrapperBuilder<E> orderDescBySort(Boolean val) {
            return val != null && val ? orderDescBySort() : this;
        }

        public WrapperBuilder<E> orderAscBySort(Boolean val) {
            return val != null && val ? orderAscBy("sort") : this;
        }

        public WrapperBuilder<E> incCurrentCoin(Long val) {
            return inc("current_coin", val);
        }

        public WrapperBuilder<E> incCommentCount(Integer val) {
            return inc("comment_count", val);
        }

        public WrapperBuilder<E> incBlogCount(Integer val) {
            return inc("blog_count", val);
        }

        public WrapperBuilder<E> incBulletCount(Integer val) {
            return inc("bullet_count", val);
        }

        public WrapperBuilder<E> incPlayCount(Integer val) {
            return inc("play_count", val);
        }

        public WrapperBuilder<E> incCoinCount(Integer val) {
            return inc("coin_count", val);
        }

        public WrapperBuilder<E> incFavoriteCount(Integer val) {
            return inc("favorite_count", val);
        }

        public WrapperBuilder<E> incForwardCount(Integer val) {
            return inc("forward_count", val);
        }

        public WrapperBuilder<E> incWeight(Integer val) {
            return inc("weight", val);
        }

        public WrapperBuilder<E> incIdx(Integer val) {
            return inc("idx", val);
        }

        public WrapperBuilder<E> incThumbsUpCount(Integer val) {
            return inc("thumbs_up_count", val);
        }

        public WrapperBuilder<E> incThumbsDownCount(Integer val) {
            return inc("thumbs_down_count", val);
        }

        public WrapperBuilder<E> incTotal(Integer val) {
            return inc("total", val);
        }

        public WrapperBuilder<E> incTotalCoin(Long val) {
            return inc("total_coin", val);
        }

        public WrapperBuilder<E> incVal(Long val) {
            return inc("val", val);
        }

        public WrapperBuilder<E> incDuration(Long val) {
            return inc("duration", val);
        }

        public WrapperBuilder<E> setAvatar(String val) {
            return set("avatar", val);
        }

        public WrapperBuilder<E> setPassword(String val) {
            return set("password", val);
        }

        public WrapperBuilder<E> setPath(String val) {
            return set("path", val);
        }

        public WrapperBuilder<E> eqPassword(String val) {
            return eq("password", val);
        }

        public WrapperBuilder<E> eqPid(Integer val) {
            return eq("pid", val);
        }

        public WrapperBuilder<E> eqLevel(Integer val) {
            return eq("level", val);
        }

        public WrapperBuilder<E> eqUserId(Integer val) {
            return eq("user_id", val);
        }

        public WrapperBuilder<E> eqFocusUserId(Integer val) {
            return eq("focus_user_id", val);
        }

        public WrapperBuilder<E> eqCommentId(Integer val) {
            return eq("comment_id", val);
        }

        public WrapperBuilder<E> eqEmail(String val) {
            return eq("email", val);
        }

        public WrapperBuilder<E> eqNickname(String val) {
            return eq("nickname", val);
        }

        public WrapperBuilder<E> eqName(String val) {
            return eq("name", val);
        }

        public WrapperBuilder<E> eqVideoId(Integer val) {
            return eq("video_id", val);
        }

        public WrapperBuilder<E> neId(Integer val) {
            return ne("id", val);
        }

        public WrapperBuilder<E> setLastLoginIp(String val) {
            return set("last_login_ip", val);
        }

        public WrapperBuilder<E> setLastLoginTime(Long val) {
            return set("last_login_time", val);
        }

        public WrapperBuilder<E> setLastLoginTime() {
            return setLastLoginTime(System.currentTimeMillis());
        }

        public WrapperBuilder<E> setUpdateTime(Long val) {
            return set("update_time", val);
        }

        public WrapperBuilder<E> setCreateTime(Long val) {
            return set("create_time", val);
        }

        public WrapperBuilder<E> setContent(String val) {
            return set("content", val);
        }

        public WrapperBuilder<E> setTop(Integer val) {
            return set("top", val);
        }

        public WrapperBuilder<E> setWeight(Integer val) {
            return set("weight", val);
        }

        public WrapperBuilder<E> setTheme(Integer val) {
            return set("theme", val);
        }

        public WrapperBuilder<E> setTitle(String val) {
            return set("title", val);
        }

        public WrapperBuilder<E> setRecommended(Boolean val) {
            return set("recommended", val);
        }

        public WrapperBuilder<E> setRuleId(Integer val) {
            return set("rule_id", val);
        }

        public WrapperBuilder<E> setUpdateTime() {
            return setUpdateTime(System.currentTimeMillis());
        }

        public WrapperBuilder<E> limitOne() {
            return limit(1L);
        }

        public WrapperBuilder<E> selectSort() {
            return select("sort");
        }

        public WrapperBuilder<E> selectState() {
            return select("state");
        }

        public WrapperBuilder<E> selectBulletCount() {
            return select("bullet_count");
        }

        public WrapperBuilder<E> selectEnabledBullet() {
            return select("enabled_bullet");
        }

        public WrapperBuilder<E> selectPlayCount() {
            return select("play_count");
        }

        public WrapperBuilder<E> selectPath() {
            return select("path");
        }

        public WrapperBuilder<E> selectUserId() {
            return select("user_id");
        }

        public WrapperBuilder<E> selectCoinCount() {
            return select("coin_count");
        }

        public WrapperBuilder<E> selectContent() {
            return select("content");
        }

        public WrapperBuilder<E> selectCurrentCoin() {
            return select("current_coin");
        }

        public WrapperBuilder<E> selectNickName() {
            return select("nick_name");
        }

        public WrapperBuilder<E> selectId() {
            return select("id");
        }

        public WrapperBuilder<E> selectRuleId() {
            return select("rule_id");
        }

        public WrapperBuilder<E> selectFavoriteCount() {
            return select("favorite_count");
        }

        public WrapperBuilder<E> selectForwardCount() {
            return select("forward_count");
        }

        public WrapperBuilder<E> selectThumbsUpCount() {
            return select("thumbs_up_count");
        }

        public WrapperBuilder<E> selectWeight() {
            return select("weight");
        }

        public WrapperBuilder<E> selectType() {
            return select("type");
        }

        public WrapperBuilder<E> selectSubmissionId() {
            return select("submission_id");
        }

        public WrapperBuilder<E> inState(Object[] vals) {
            return in("state", vals);
        }

        public WrapperBuilder<E> inType(Object[] vals) {
            return in("type", vals);
        }

        public WrapperBuilder<E> eqState(Object val) {
            return eq("state", val);
        }

        public WrapperBuilder<E> setState(Object val) {
            return set("state", val);
        }

        public WrapperBuilder<E> setStatus(Object val) {
            return set("status", val);
        }

        public WrapperBuilder<E> eqSubmissionId(Integer val) {
            return eq("submission_id", val);
        }

        public WrapperBuilder<E> eqSeriesId(Integer val) {
            return eq("series_id", val);
        }

        public WrapperBuilder<E> setDuration(Object val) {
            return set("duration", val);
        }

        public WrapperBuilder<E> setName(Object val) {
            return set("name", val);
        }

        public WrapperBuilder<E> setIntroduction(Object val) {
            return set("introduction", val);
        }

        public WrapperBuilder<E> gteTime(Long val) {
            return gte("time", val);
        }

        public WrapperBuilder<E> gteFrom(Long val) {
            return gte("from", val);
        }

        public WrapperBuilder<E> gteCreateTime(Long val) {
            return gte("create_time", val);
        }

        public WrapperBuilder<E> gteWeight(Integer val) {
            return gte("weight", val);
        }

        public WrapperBuilder<E> lteWeight(Integer val) {
            return lte("weight", val);
        }

        public WrapperBuilder<E> gteIdx(Integer val) {
            return gte("idx", val);
        }

        public WrapperBuilder<E> lteIdx(Integer val) {
            return lte("idx", val);
        }

        public WrapperBuilder<E> gteCurrentCoin(Integer val) {
            return gte("current_coin", val);
        }

        public WrapperBuilder<E> lteTime(Long val) {
            return lte("time", val);
        }

    }
}
