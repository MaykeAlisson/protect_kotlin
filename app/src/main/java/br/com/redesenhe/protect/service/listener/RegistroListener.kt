package br.com.redesenhe.protect.service.listener

interface RegistroListener {

    /**
     * Click para edição
     */
    fun onListClick(id: Int, nome: String)

    /**
     * Remoção
     */
    fun onDeleteClick(id: Int, nome: String)
}