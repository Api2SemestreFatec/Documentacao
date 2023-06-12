/*
 * The MIT License
 *
 * Copyright 2023 daviramos.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.lacamentohoraextra.Models;

/**
 *
 * @author daviramos
 */
public class ParametrizacaoModel {

    private String cliente;
    private String projeto;
    private String data_fechamento;
    private String tipo_hora;
    private String periodo;
    private String percentual;
    private String adicional_noturno;
    private String horario_inicio;
    private String horario_fim;

    public ParametrizacaoModel() {
    }
    
    public ParametrizacaoModel(String cliente, String projeto, String data_fechamento, String tipo_hora, String periodo, String percentual, String adicional_noturno, String horario_inicio, String horario_fim) {
        this.cliente = cliente;
        this.projeto = projeto;
        this.data_fechamento = data_fechamento;
        this.tipo_hora = tipo_hora;
        this.periodo = periodo;
        this.percentual = percentual;
        this.adicional_noturno = adicional_noturno;
        this.horario_inicio = horario_inicio;
        this.horario_fim = horario_fim;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public String getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(String data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    public String getTipo_hora() {
        return tipo_hora;
    }

    public void setTipo_hora(String tipo_hora) {
        this.tipo_hora = tipo_hora;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPercentual() {
        return percentual;
    }

    public void setPercentual(String percentual) {
        this.percentual = percentual;
    }

    public String getAdicional_noturno() {
        return adicional_noturno;
    }

    public void setAdicional_noturno(String adicional_noturno) {
        this.adicional_noturno = adicional_noturno;
    }

    public String getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(String horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public String getHorario_fim() {
        return horario_fim;
    }

    public void setHorario_fim(String horario_fim) {
        this.horario_fim = horario_fim;
    }
}
