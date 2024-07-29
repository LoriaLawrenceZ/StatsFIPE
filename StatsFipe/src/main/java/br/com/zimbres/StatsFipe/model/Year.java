package br.com.zimbres.StatsFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Year(@JsonAlias("codigo") String code,
                   @JsonAlias("nome") String name) {}
