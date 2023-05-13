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

import br.com.lacamentohoraextra.DAO.ApontamentoDAO;

/**
 *
 * @author daviramos
 */
public class ApontamentoModel {

    private String dataInicialApontamento;
    private String dataFinalApontamento;
    private String data_apontamento;
    private String horaInicio;
    private String horaFinal;
    private String hora_apontamento;
    private String projeto;
    private String solicitante;
    private String cliente_projeto;
    private String intervalo;
    private String justificativa;
    private String situacao;

    public ApontamentoModel() {
    }

    public ApontamentoModel(String dataInicialApontamento, String dataFinalApontamento, String data_apontamento, String horaInicio, String horaFinal, String hora_apontamento, String projeto, String solicitante, String cliente_projeto, String intervalo, String justificativa, String situacao) {
        this.dataInicialApontamento = dataInicialApontamento;
        this.dataFinalApontamento = dataFinalApontamento;
        this.data_apontamento = data_apontamento;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.hora_apontamento = hora_apontamento;
        this.projeto = projeto;
        this.solicitante = solicitante;
        this.cliente_projeto = cliente_projeto;
        this.intervalo = intervalo;
        this.justificativa = justificativa;
        this.situacao = situacao;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public String getDataInicialApontamento() {
        return dataInicialApontamento;
    }

    public void setDataInicialApontamento(String dataInicialApontamento) {
        this.dataInicialApontamento = dataInicialApontamento;
    }

    public String getDataFinalApontamento() {
        return dataFinalApontamento;
    }

    public void setDataFinalApontamento(String dataFinalApontamento) {
        this.dataFinalApontamento = dataFinalApontamento;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getData_apontamento() {
        return data_apontamento;
    }

    public void setData_apontamento(String data_apontamento) {
        this.data_apontamento = data_apontamento;
    }

    public String getHora_apontamento() {
        return hora_apontamento;
    }

    public void setHora_apontamento(String hora_apontamento) {
        this.hora_apontamento = hora_apontamento;
    }

    public String getCliente_projeto() {
        return cliente_projeto;
    }

    public void setCliente_projeto(String cliente_projeto) {
        this.cliente_projeto = cliente_projeto;
    }
    
    public void cadastarHoraExtra(ApontamentoModel apontamento) {
        new ApontamentoDAO().cadastrarApontamento(apontamento);
    }
}
