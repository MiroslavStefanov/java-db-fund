package app.exam.parser;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.exam.parser.interfaces.ModelParser;

@Component
public class ModelParserImpl implements ModelParser {

    private final ModelMapper mapper;

    @Autowired
    public ModelParserImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        D ret = this.mapper.map(source, destinationClass);
        return ret;
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass, PropertyMap<S, D> propertyMap) {
        return null;
    }
}
