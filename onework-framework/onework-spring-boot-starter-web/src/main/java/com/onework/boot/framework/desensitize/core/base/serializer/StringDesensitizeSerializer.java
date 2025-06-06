package com.onework.boot.framework.desensitize.core.base.serializer;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.onework.boot.framework.desensitize.core.base.annotation.DesensitizeBy;
import com.onework.boot.framework.desensitize.core.base.handler.DesensitizationHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 脱敏序列化器
 * <p>
 * 实现 JSON 返回数据时，使用 {@link DesensitizationHandler} 对声明脱敏注解的字段，进行脱敏处理。
 * 支持多种脱敏策略，通过 {@link DesensitizeBy} 注解指定具体的脱敏处理器。
 *
 * @author onework
 */
@SuppressWarnings("rawtypes")
public class StringDesensitizeSerializer extends StdSerializer<String> implements ContextualSerializer {

    @Getter
    @Setter
    private DesensitizationHandler desensitizationHandler;

    /**
     * 默认构造函数
     */
    protected StringDesensitizeSerializer() {
        super(String.class);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) {
        if (beanProperty == null) {
            return this;
        }

        DesensitizeBy annotation = beanProperty.getAnnotation(DesensitizeBy.class);
        if (annotation == null) {
            return this;
        }

        // 创建一个新的序列化器实例，并设置对应的脱敏处理器
        StringDesensitizeSerializer serializer = new StringDesensitizeSerializer();
        serializer.setDesensitizationHandler(Singleton.get(annotation.handler()));
        return serializer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        // 处理空值情况
        if (StrUtil.isBlank(value)) {
            gen.writeNull();
            return;
        }

        // 获取当前序列化的字段
        Field field = getField(gen);
        if (field == null) {
            writeString(gen, value);
            return;
        }

        // 获取字段上的所有脱敏注解
        DesensitizeBy[] annotations = AnnotationUtil.getCombinationAnnotations(field, DesensitizeBy.class);
        if (ArrayUtil.isEmpty(annotations)) {
            writeString(gen, value);
            return;
        }

        // 处理脱敏注解
        for (Annotation annotation : field.getAnnotations()) {
            if (AnnotationUtil.hasAnnotation(annotation.annotationType(), DesensitizeBy.class)) {
                value = this.desensitizationHandler.desensitize(value, annotation);
                writeString(gen, value);
                return;
            }
        }

        writeString(gen, value);
    }

    /**
     * 获取当前序列化的字段
     *
     * @param generator JsonGenerator
     * @return 字段，如果获取失败则返回 null
     */
    private Field getField(JsonGenerator generator) {
        try {
            String currentName = generator.getOutputContext().getCurrentName();
            Object currentValue = generator.getCurrentValue();
            if (currentName == null || currentValue == null) {
                return null;
            }
            return ReflectUtil.getField(currentValue.getClass(), currentName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 写入字符串值
     *
     * @param gen JsonGenerator
     * @param value 要写入的值
     * @throws IOException 如果写入失败
     */
    private void writeString(JsonGenerator gen, String value) throws IOException {
        gen.writeString(value);
    }
}
