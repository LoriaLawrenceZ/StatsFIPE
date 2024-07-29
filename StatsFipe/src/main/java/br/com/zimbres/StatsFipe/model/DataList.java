package br.com.zimbres.StatsFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DataList(@JsonAlias("modelos") List<Model> models,
                       @JsonAlias("anos") List<Year> years) {
}
