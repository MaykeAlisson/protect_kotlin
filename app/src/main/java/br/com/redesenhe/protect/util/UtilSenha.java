package br.com.redesenhe.protect.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UtilSenha {

    public static String gerarSenha(
            int tamanho, Boolean maiuscula,
            Boolean minuscula, Boolean digito,
            Boolean underline, Boolean especial
    ) {
        if (!maiuscula && !minuscula && !digito && !underline && !especial) return "";

        String mai = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";
        String min = "abcdefghijklmnopqrstuvxyzw";
        String digi = "0123456789";
        String und = "_";
        String espe = "?@#$%!&*";
        String maiMin = "ABCDEFGHIJKLMNOPQRSTUVYWXZabcdefghijklmnopqrstuvxyzw";
        String maiMinNum = "ABCDEFGHIJKLMNOPQRSTUVYWXZabcdefghijklmnopqrstuvxyzw0123456789";

        Random random = new Random();

        StringBuilder armazenaChaves = new StringBuilder();
        List<Character> catacteres = new ArrayList<>();
        int index = -1;

        if (underline) catacteres.add(und.charAt(0));
        if (especial) catacteres.add(espe.charAt(random.nextInt(espe.length())));
        if (digito) catacteres.add(digi.charAt(random.nextInt(digi.length())));

        if (catacteres.size() < tamanho) {
            while (catacteres.size() < tamanho) {
                if (maiuscula && minuscula && digito) {
                    catacteres.add(maiMinNum.charAt(random.nextInt(maiMinNum.length())));
                    continue;
                }
                if (maiuscula && minuscula) {
                    catacteres.add(maiMin.charAt(random.nextInt(maiMin.length())));
                    continue;
                }
                if (maiuscula) {
                    catacteres.add(mai.charAt(random.nextInt(mai.length())));
                    continue;
                }
                if (minuscula) {
                    catacteres.add(min.charAt(random.nextInt(min.length())));
                    continue;
                }
                if (digito) {
                    catacteres.add(digi.charAt(random.nextInt(digi.length())));
                    continue;
                }
                if (underline) {
                    catacteres.add(und.charAt(0));
                    continue;
                }
                if (especial) {
                    catacteres.add(espe.charAt(random.nextInt(espe.length())));
                }
            }

        }
        Collections.shuffle(catacteres);

        for (Character car : catacteres) {
            armazenaChaves.append(car);
        }
        return armazenaChaves.toString();
    }
}
