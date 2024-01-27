package evaluacion2;

/**
 *
 */
public interface IObservador {

    /**
     *
     * @param objObservado Es el Objeto observado
     * @param valor Es el valor que ha cambiado, puede ser cualquier tipo de objeto valido de java
     */
    public void observadoActualizado(Observado objObservado, Object valor);

}
