package br.com.zimbres.StatsFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertData implements InterfaceConvertData{

    private ObjectMapper oMapper = new ObjectMapper();

    @Override
    public <T> T getData (String pJson, Class<T> pClass) {
        try {
            return oMapper.readValue(pJson, pClass);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    @Override
    public <T> List<T> getDataList (String pJson, Class<T> pClass) {
        CollectionType list = oMapper.getTypeFactory()
                .constructCollectionType(List.class, pClass);
        try {
            return oMapper.readValue(pJson, list);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }
}
