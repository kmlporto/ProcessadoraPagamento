package models;

public class MessageRespRequest {
    private String agencia, conta, numeroCartao;
    private float valor;
    private int senha;
    private boolean aprovado;

    public MessageRespRequest() {
    }

    public MessageRespRequest(String agencia, String conta, String numeroCartao, float valor, int senha) {
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.senha = senha;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public void setObjectMessage(MessageRespRequest respRequest1){
        this.setAgencia(respRequest1.getAgencia());
        this.setConta(respRequest1.getConta());
        this.setNumeroCartao(respRequest1.getNumeroCartao());
        this.setValor(respRequest1.getValor());
    }
}
