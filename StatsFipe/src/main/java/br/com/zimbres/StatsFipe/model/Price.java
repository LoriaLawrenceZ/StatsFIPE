package br.com.zimbres.StatsFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Price(@JsonAlias("TipoVeiculo") Integer typeVehicle,
                    @JsonAlias("Valor") String price,
                    @JsonAlias("Marca") String brand,
                    @JsonAlias("Modelo") String model,
                    @JsonAlias("AnoModelo") Integer year,
                    @JsonAlias("Combustivel") String fuel,
                    @JsonAlias("CodigoFipe") String fipeCode,
                    @JsonAlias("MesReferencia") String referenceMonth,
                    @JsonAlias("SiglaCombustivel") String fuelAbbr) {}
