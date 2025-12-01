package br.com.ifba.feiraplus.infrastructure.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();
        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public <Input, Output> Output map(final Input object, final Class<Output> clazz) {
        return MODEL_MAPPER.map(object, clazz);
    }

    public <Input, Output> List<Output> mapAll(final List<Input> source, final Class<Output> clazz) {
        return source.stream()
                .map(element -> map(element, clazz))
                .collect(Collectors.toList());
    }
}