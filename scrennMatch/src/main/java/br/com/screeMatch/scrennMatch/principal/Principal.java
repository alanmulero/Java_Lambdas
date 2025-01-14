package br.com.screeMatch.scrennMatch.principal;

import br.com.screeMatch.scrennMatch.model.DadosEpisodio;
import br.com.screeMatch.scrennMatch.model.DadosSerie;
import br.com.screeMatch.scrennMatch.model.DadosTemporada;
import br.com.screeMatch.scrennMatch.service.ConsumoApi;
import br.com.screeMatch.scrennMatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private String API_KEY = "&apikey=6585022c";
    private ConsumoApi consumoApi = new ConsumoApi();
    private  ConverteDados conversor = new ConverteDados();

    Scanner leitura = new Scanner(System.in);
    public void exibeMenu() {
        System.out.println("Digite o nome da série para pesquisar");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);
        // Dados de Temporadas
        // código omitido

        List<DadosTemporada> temporadas = new ArrayList<>();

        DadosTemporada dadosTemporada = null;
        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);

        }
        temporadas.forEach(System.out::println);

//        // Imprimindo só os titulos
//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodios = temporadas.get(i).episodios();
//            for (int j = 0; j < episodios.size(); j++) {
//                System.out.println(episodios.get(j).titulo());
//            }
//
//        }

        // A partir do Java 8
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}




