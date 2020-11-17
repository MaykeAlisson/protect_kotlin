package br.com.redesenhe.protect.service.listener

interface GrupoListener {

    /**
     * Click para edição
     */
    fun onListClick(id: Int)

    /**
     * Remoção
     */
    fun onDeleteClick(id: Int)

}