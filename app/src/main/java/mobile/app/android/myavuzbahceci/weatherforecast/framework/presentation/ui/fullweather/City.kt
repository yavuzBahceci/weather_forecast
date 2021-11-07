package mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather

import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.ui.fullweather.City.*

enum class City(val value: String) {
    PARIS("Paris"),
    ISTANBUL("Istanbul"),
    NEW_YORK("New York"),
    IBIZA("Ibiza Town"),
    NICE("Arrondissement de Nice"),
    BERLIN("Berlin"),
    LONDON("London"),
    GAZIANTEP("Gaziantep"),
    ANKARA("Ankara"),
    MADRID("Madrid"),
}

fun getSampleCities(): List<City> {
    return listOf(
        PARIS, ISTANBUL, NEW_YORK, IBIZA, NICE, BERLIN, LONDON, GAZIANTEP, ANKARA, MADRID
    )
}

fun getCity(value: String): City? {
    val map = values().associateBy(City::value)
    return map[value]
}