/*
 * SummerCacheCollectionKeyGenerator.java
 */

package com.fs.web.cache;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

@Component
public class ExtensionCacheCollectionKeyGenerator implements KeyGenerator {

    @Override
    @SuppressWarnings("unchecked")
    public Object generate(final Object target, final Method method, final Object... params) {
        if (params.length > 0 && params[0] instanceof Collection) {
            if (method.getAnnotation(Cacheable.class) != null) {
                final Class<?> returnType = method.getReturnType();
                if (!Map.class.isAssignableFrom(returnType)) {
                    throw new IllegalStateException(
                            "return type must map");
                }
            }

            return (Collection<Object>) params[0];
        }
        throw new IllegalStateException("ExtensionCacheCollectionKeyGenerator failed");
    }

}
