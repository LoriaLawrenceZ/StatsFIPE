package br.com.zimbres.StatsFipe.principal;

import br.com.zimbres.StatsFipe.model.*;
import br.com.zimbres.StatsFipe.service.APIConsumption;
import br.com.zimbres.StatsFipe.service.ConvertData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    Scanner scanner = new Scanner(System.in, "latin1");

    private final APIConsumption oApiConsumption = new APIConsumption();
    private final ConvertData oConvertData = new ConvertData();

    private Brand oBrand;
    private Model oModel;
    private Year oYear;
    private Price oPrice;

    private List<Brand> brands;
    private List<Model> models;
    private List<Year> years;

    private String chosenBrand;
    private String chosenModel;
    private String chosenYear;

    private String url = ENDERECO;
    private String jsonBody;

    public void menu() {
        String chosenVehicle;
        do {
            System.out.println("Escolha o tipo de veículo:\n[1] - Carros\n[2] - Motos\n[3] - Caminhões");
            chosenVehicle = scanner.nextLine();
        } while (!chosenVehicle.equals("1") && !chosenVehicle.equals("2") && !chosenVehicle.equals("3"));

        switch (chosenVehicle) {
            case "1":
                url += "carros/marcas";
                searchBrand(url, "carros");
                break;
            case "2":
                url += "motos/marcas";
                searchBrand(url, "motos");
                break;
            case "3":
                url += "caminhoes/marcas";
                searchBrand(url, "caminhoes");
                break;
        }

        do {
            System.out.println("Digite o código da marca desejada:");
            chosenBrand = scanner.nextLine();
        } while (brands.stream().noneMatch(brand -> brand.code().equals(chosenBrand)));

        Optional<Brand> searchedBrand = brands.stream().filter(brand -> brand.code().equals(chosenBrand)).findFirst();
        searchedBrand.ifPresentOrElse(brand -> oBrand = brand, () -> {
            System.out.println("Marca não encontrada");
            System.exit(0);
        });

        url += "/" + oBrand.code() + "/modelos";
        
        searchModel(url);

        do {
            System.out.println("Digite o código ou nome do modelo desejado:");
            chosenModel = scanner.nextLine();
        } while (models.stream().noneMatch(model -> model.code().equals(chosenModel) || model.name().contains(chosenModel)));

        List<Model> searchedModel = models.stream()
                .filter(model -> model.code().equals(chosenModel) || model.name().contains(chosenModel))
                .collect(Collectors.toList());

        searchedModel.forEach(model -> {
                    oModel = model;
                    System.out.println("Código: " + model.code() + " - Modelo: " + model.name());
                });

        do {
            System.out.println("Digite o código do modelo desejado para precificação:");
            chosenModel = scanner.nextLine();
        } while (models.stream().noneMatch(model -> model.code().equals(chosenModel)));

        Optional<Model> searchedFinalModel = models.stream().filter(model -> model.code().equals(chosenModel)).findFirst();
        searchedFinalModel.ifPresentOrElse(model -> oModel = model, () -> {
            System.out.println("Modelo não encontrado");
            System.exit(0);
        });

        url += "/" + oModel.code() + "/anos";

        searchYear(url);

        do {
            System.out.println("Digite o código do ano desejado:");
            chosenYear = scanner.nextLine();
        } while (years.stream().noneMatch(year -> year.code().equals(chosenYear)));

        Optional<Year> searchedYear = years.stream().filter(year -> year.code().equals(chosenYear)).findFirst();
        searchedYear.ifPresentOrElse(year -> oYear = year, () -> {
            System.out.println("Ano não encontrado");
            System.exit(0);
        });

        url += "/" + oYear.code();

        searchPrice(url);

    }

    /**
     * Searches for brands based on the provided URL and vehicle type.
     *
     * @param pUrl     The URL to fetch the brand data from.
     * @param pVehicle The type of vehicle (e.g., "carros", "motos", "caminhoes").
     */
    private void searchBrand (String pUrl, String pVehicle) {
        jsonBody = oApiConsumption.getData(url);

        brands = oConvertData.getDataList(jsonBody, Brand.class);

        System.out.println("Todas as marcas de " + pVehicle + ":");
        brands.stream()
                .sorted(Comparator.comparing(Brand::code))
                .forEach(brand -> System.out.println("Código: " + brand.code() + " - Marca: " + brand.name()));
    }

    /**
     * Searches for models based on the provided URL.
     *
     * @param pUrl The URL to fetch the model data from.
     */
    private void searchModel (String pUrl) {
        jsonBody = oApiConsumption.getData(url);

        DataList oDataList = oConvertData.getData(jsonBody, DataList.class);
        models = oDataList.models();

        System.out.println("Todos os modelos de " + oBrand.name() + " (" + oBrand.code() + "):");
        models.stream()
                .sorted(Comparator.comparing(Model::code))
                .forEach(model -> System.out.println("Código: " + model.code() + " - Modelo: " + model.name()));
    }

    /**
     * Searches for years based on the provided URL.
     *
     * @param pUrl The URL to fetch the year data from.
     */
    private void searchYear (String pUrl) {
        jsonBody = oApiConsumption.getData(url);

        years = oConvertData.getDataList(jsonBody, Year.class);

        System.out.println("Todas os anos de " + "veiculo" + ":");
        years.stream()
                .sorted(Comparator.comparing(Year::code))
                .forEach(year -> System.out.println("Código: " + year.code() + " - Ano: " + year.name()));
    }

    /**
     * Searches for the price based on the provided URL.
     *
     * @param pUrl The URL to fetch the price data from.
     */
    private void searchPrice (String pUrl) {
        jsonBody = oApiConsumption.getData(url);

        oPrice = oConvertData.getData(jsonBody, Price.class);

        System.out.println("Preço do veículo:\n" + oPrice.toString());
    }
}
