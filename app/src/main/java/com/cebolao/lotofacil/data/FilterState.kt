package com.cebolao.lotofacil.data

// Esta data class representa o estado de um único filtro na UI.
data class FilterState(
    val type: FilterType,
    val isEnabled: Boolean = false,
    val selectedRange: ClosedFloatingPointRange<Float> = type.defaultRange
)