package com.github.zkoalas.jwts.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import com.github.zkoalas.jwts.TokenInterceptor;
import com.github.zkoalas.jwts.provider.*;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

/**
 * 框架配置
 */
@Configuration
@EnableConfigurationProperties(JwtSoulProperties.class)
public class JwtSoulConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    @Autowired
    private JwtSoulProperties properties;
    private ApplicationContext applicationContext;

    @Bean
    public TokenStore tokenStore() {
        if(properties.getStoreType() == 1){
            Collection<StringRedisTemplate> stringRedisTemplates = applicationContext.getBeansOfType(StringRedisTemplate.class).values();
            if (stringRedisTemplates.size() > 0) {
                return new RedisTokenStore(stringRedisTemplates.iterator().next());
            }
        }else {
            Collection<DataSource> dataSources = applicationContext.getBeansOfType(DataSource.class).values();
            if (dataSources.size() > 0) {
                return new JdbcTokenStore(dataSources.iterator().next());
            }
        }
        return null;
    }

    @Bean
    public LocalTokenStore localTokenStore() {
        if (properties.getStoreType() == null || properties.getStoreType() == 0) {
            return new LocalTokenStore(properties.getSecretKey(), properties.getMd5Key(), properties.getExpiration());
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] path = properties.getPath();
        if (path == null || path.length <= 0) {
            path = new String[]{"/**"};
        }
        String[] excludePath = properties.getExcludePath();
        if (excludePath == null) {
            excludePath = new String[]{};
        }
        if (properties.getStoreType() == null || properties.getStoreType() == 0) {
            registry.addInterceptor(new TokenInterceptor(localTokenStore()))
                    .addPathPatterns(path)
                    .excludePathPatterns(excludePath);
        }else{
            registry.addInterceptor(new TokenInterceptor(tokenStore(), properties.getMaxToken()))
                    .addPathPatterns(path)
                    .excludePathPatterns(excludePath);
        }
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
