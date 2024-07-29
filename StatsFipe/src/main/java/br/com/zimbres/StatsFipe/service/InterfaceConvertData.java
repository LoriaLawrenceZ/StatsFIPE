package br.com.zimbres.StatsFipe.service;

import java.util.List;

public interface InterfaceConvertData {

    <T> T getData (String pJson, Class<T> pClass);

    <T> List<T> getDataList (String pJson, Class<T> pClass);
}
