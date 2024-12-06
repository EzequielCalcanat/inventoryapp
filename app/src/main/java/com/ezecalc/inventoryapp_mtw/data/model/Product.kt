package com.ezecalc.inventoryapp_mtw.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Product(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("codigo_barras")
    val codigo_barras: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("fecha_actualizacion")
    val fecha_actualizacion: String,

    @SerializedName("nombre")
    val nombre: String
)