package com.ariel.disha.mall.config.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.consts.*;
import com.ariel.disha.mall.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author ariel
 * @apiNote JwtAuthFilter
 * @serial
 */
@WebFilter("/*")
public class JwtAuthFilter implements Filter {

    @Autowired
    private AppProperties appProperties;

    private static final String[] EXCLUDE_PATTERNS = {"/user/login", "/open/", "/test/"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            doFilterInternal(((HttpServletRequest) request), ((HttpServletResponse) response), chain);
        }else {
            chain.doFilter(request, response);
        }
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        for (String excludePattern : EXCLUDE_PATTERNS) {
            if (request.getServletPath().startsWith(excludePattern)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(authorization)) {
            errorResponse(response, HttpStatus.AUTH_HEADER_IS_NULL_ERROR);
            return;
        }
        String token = authorization.substring(HttpConst.BEARER.length());
        if (StrUtil.isBlank(token)) {
            errorResponse(response, HttpStatus.AUTH_HEADER_IS_NULL_ERROR);
            return;
        }
        TokenVo tokenVo = JwtUtil.decodeToken(token, appProperties.getSecret());
        if (tokenVo == null) {
            errorResponse(response, HttpStatus.AUTH_ERROR);
            return;
        }

        HttpServletRequestWrapper requestWrapper = new BodyHttpServletRequestWrapper(request, tokenVo);
        filterChain.doFilter(requestWrapper, response);
    }

    private void errorResponse(HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSONUtil.toJsonStr(HttpResponse.error(httpStatus)));
    }

    private static class BodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final Map<String, String[]> map = new HashMap<>();

        private final TokenVo tokenVo;

        public BodyHttpServletRequestWrapper(HttpServletRequest request, TokenVo tokenVo) {
            super(request);
            this.tokenVo = tokenVo;
        }

        // 表单类型
        @Override
        public Enumeration<String> getParameterNames() {
            if (map.isEmpty()) {
                map.putAll(getRequest().getParameterMap());
                JSONUtil.parseObj(tokenVo)
                        .forEach((k, v) -> map.put(k, new String[]{v.toString()}));
            }
            return Collections.enumeration(map.keySet());
        }

        // 表单类型
        @Override
        public String[] getParameterValues(String name) {
            return map.get(name);
        }

        // Json类型
        @Override
        public ServletInputStream getInputStream() throws IOException {
            MediaType mediaType = MediaType.parseMediaType(getRequest().getContentType());
            if (MediaType.APPLICATION_JSON.includes(mediaType)) {
                String s = new String(getRequest().getInputStream().readAllBytes());
                JSONObject jsonObject = JSONUtil.parseObj(s);
                jsonObject.putAll(JSONUtil.parseObj(tokenVo));
                String body = jsonObject.toString();
                return new BodyInputStream(body.getBytes(StandardCharsets.UTF_8));
            } else {
                return getRequest().getInputStream();
            }
        }
    }

    private static class BodyInputStream extends ServletInputStream {

        private final InputStream delegate;

        public BodyInputStream(byte[] body) {
            this.delegate = new ByteArrayInputStream(body);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return this.delegate.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return this.delegate.read(b, off, len);
        }

        @Override
        public int read(byte[] b) throws IOException {
            return this.delegate.read(b);
        }

        @Override
        public long skip(long n) throws IOException {
            return this.delegate.skip(n);
        }

        @Override
        public int available() throws IOException {
            return this.delegate.available();
        }

        @Override
        public void close() throws IOException {
            this.delegate.close();
        }

        @Override
        public synchronized void mark(int readlimit) {
            this.delegate.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            this.delegate.reset();
        }

        @Override
        public boolean markSupported() {
            return this.delegate.markSupported();
        }
    }
}
