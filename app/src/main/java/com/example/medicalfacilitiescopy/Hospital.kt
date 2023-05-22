package com.example.medicalfacilitiescopy

data class Hospital(val name:String="",
                    val address:String="",
                    val facilities:List<String> = listOf(),
                    val latitude: Double,
                    val longitude: Double

)
{
    constructor() : this("", "", listOf(), 0.0, 0.0)
}