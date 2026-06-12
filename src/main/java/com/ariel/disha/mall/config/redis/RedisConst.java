package com.ariel.disha.mall.config.redis;

/**
 * @author ariel
 * @apiNote RedisConst
 * @serial
 */
public class RedisConst {

    public static final String CHECK_CODE_PREFIX = "checkCode:";

    public static final String USER_TOKEN_PREFIX = "user:token:";

    public static final String USER_INFO_PREFIX = "user:info:";

    public static final String CATEGORY_SORT_PREFIX = "category:sort";

    public static final String FILE_UPLOAD_PREFIX = "file:upload:";

    public static final String VIDEO_QUEUE = "video:queue";

    public static final String VIDEO_QUEUE_TRANSCODED = "video:queue:transcoded";

    public static final String VIDEO_ONLINE_USERS_PREFIX = "video:online:users:";

    public static final String COMMENT_TOP_LOCK_PREFIX = "comment:top:lock:";

    public static final String SEARCH_SUBMISSION_HISTORY_PREFIX = "search:submission:history:";

    public static final String SUBMISSION_HOT_SEARCH_PREFIX = "search:submission:hot";

    public static final String DELAYED_QUEUE_ACTIVE = "delayed:queue:active";

    public static final String DELAYED_QUEUE_FAILED = "delayed:queue:failed";

    public static final String STATS_AUTHOR = "stats:author:";

    public static String getVideoQueueKey() {
        return VIDEO_QUEUE;
    }

    public static String getVideoTranscodedQueueKey() {
        return VIDEO_QUEUE_TRANSCODED;
    }

    public static String getFileUploadKey(String id) {
        return FILE_UPLOAD_PREFIX + id;
    }

    public static String getVideoOnlineUsersKey(Integer videoId) {
        return VIDEO_ONLINE_USERS_PREFIX + videoId;
    }

    public static String getVideoOnlineUsersKey(String pattern) {
        return VIDEO_ONLINE_USERS_PREFIX + pattern;
    }

    public static String getCommentTopLockKey(Integer videoId) {
        return COMMENT_TOP_LOCK_PREFIX + videoId;
    }

    public static String getSearchSubmissionHistoryKey(Integer id) {
        return SEARCH_SUBMISSION_HISTORY_PREFIX + id;
    }

    public static String getSubmissionHotSearchKey() {
        return SUBMISSION_HOT_SEARCH_PREFIX;
    }

    public static String getDelayedQueueActiveKey() {
        return DELAYED_QUEUE_ACTIVE;
    }

    public static String getDelayedQueueFailedKey() {
        return DELAYED_QUEUE_FAILED;
    }

    public static String getStatsKey(Integer authorId) {
        return STATS_AUTHOR + authorId;
    }

    public static String getUserInfoPrefix(Integer userId) {
        return USER_INFO_PREFIX + userId;
    }


}
