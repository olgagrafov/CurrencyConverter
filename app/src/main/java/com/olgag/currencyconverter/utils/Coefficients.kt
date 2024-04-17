package com.olgag.currencyconverter.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.olgag.currencyconverter.R

sealed class ConversionScreens(@StringRes val titleStringRes: Int, @DrawableRes val iconDrawableResource: Int, val idTab: Int) {
    object ConversionLength: ConversionScreens(R.string.length, R.drawable.length, 0)
    object ConversionWeight: ConversionScreens(R.string.weight, R.drawable.scale, 1)
    object ConversionTemperature: ConversionScreens(R.string.temperature, R.drawable.thermometer, 2)
}
val tabs = listOf(
    ConversionScreens.ConversionLength,
    ConversionScreens.ConversionWeight,
    ConversionScreens.ConversionTemperature
)
/*****************************************************/
sealed class TemperatureCoefficients(@StringRes val temperatureFrom: Int, @StringRes val temperatureTo: Int, val coefficient: Float ) {
    object CelsiusFahrenheit: TemperatureCoefficients(R.string.celsius, R.string.fahrenheit, 32F)
    object CelsiusKelvin: TemperatureCoefficients(R.string.celsius, R.string.kelvin, 273.15F)
    object FahrenheitKelvin: TemperatureCoefficients(R.string.fahrenheit, R.string.kelvin,  273.15F)
    object FahrenheitCelsius: TemperatureCoefficients(R.string.fahrenheit, R.string.celsius, 32F)
    object KelvinFahrenheit: TemperatureCoefficients(R.string.kelvin, R.string.fahrenheit, -273.15F)
    object KelvinCelsius: TemperatureCoefficients(R.string.kelvin, R.string.celsius, 273.15F)
 }
val temperatureList = listOf(
    TemperatureCoefficients.CelsiusFahrenheit,
    TemperatureCoefficients.CelsiusKelvin,
    TemperatureCoefficients.FahrenheitKelvin,
    TemperatureCoefficients.FahrenheitCelsius,
    TemperatureCoefficients.KelvinFahrenheit,
    TemperatureCoefficients.KelvinCelsius
)

/*****************************************************/
sealed class WeightCoefficients(@StringRes val weightFrom: Int, @StringRes val weightTo: Int, val coefficient: Float ) {
    object KilogramPound: WeightCoefficients(R.string.kilogram, R.string.pound,2.2046244F)
    object KilogramStones: WeightCoefficients(R.string.kilogram, R.string.stones,0.15747F)
    object PoundKilogram: WeightCoefficients(R.string.pound, R.string.kilogram,0.453592F)
    object PoundOunce: WeightCoefficients(R.string.pound, R.string.ounce,16F)
    object StonesKilogram: WeightCoefficients(R.string.stones, R.string.kilogram,6.35F)
    object GramOunce: WeightCoefficients(R.string.gram, R.string.ounce,0.03527399F)
    object GramCarat: WeightCoefficients(R.string.gram, R.string.carat,5F)
    object OunceGram: WeightCoefficients(R.string.ounce, R.string.gram,28.3495F)
    object OuncePound: WeightCoefficients(R.string.ounce, R.string.pound,0.0625F)
    object OunceCarat: WeightCoefficients(R.string.ounce, R.string.carat,141.7475F)
    object CaratGram: WeightCoefficients(R.string.carat, R.string.gram,0.2F)
    object CaratOunce: WeightCoefficients(R.string.carat, R.string.ounce,0.0070547983F)
}
val weightList = listOf(
    WeightCoefficients.KilogramPound,
    WeightCoefficients.KilogramStones,
    WeightCoefficients.StonesKilogram,
    WeightCoefficients.CaratOunce,
    WeightCoefficients.CaratGram,
    WeightCoefficients.GramOunce,
    WeightCoefficients.GramCarat,
    WeightCoefficients.OunceCarat,
    WeightCoefficients.OunceGram,
    WeightCoefficients.OuncePound,
    WeightCoefficients.PoundOunce,
    WeightCoefficients.PoundKilogram
)
/*****************************************************/
sealed class LengthCoefficients(@StringRes val lengthFrom: Int, @StringRes val lengthTo: Int, val coefficient: Float ) {
    object KilometerMile: LengthCoefficients(R.string.kilometer, R.string.mile, 0.6213689F)
    object MileKilometer: LengthCoefficients(R.string.mile, R.string.kilometer, 1.60935F)
    object NauticalMileKilometer: LengthCoefficients(R.string.nautical_mile, R.string.kilometer, 1.852F)
    object KilometerNauticalMile: LengthCoefficients(R.string.kilometer, R.string.nautical_mile, 0.539957F)
    object MeterFeet: LengthCoefficients(R.string.meter, R.string.feet, 3.28084F)
    object FeetMeter: LengthCoefficients(R.string.feet, R.string.meter, 0.3048F)
    object InchCentimeter: LengthCoefficients(R.string.inch, R.string.centimeter, 2.54F)
    object CentimeterInch: LengthCoefficients(R.string.centimeter, R.string.inch, 0.39370078F)
    object InchMillimeter: LengthCoefficients(R.string.inch, R.string.millimeter, 25.4F)
    object MillimeterInch: LengthCoefficients(R.string.millimeter, R.string.inch, 0.0032808399F)
    object InchFeet: LengthCoefficients(R.string.inch, R.string.feet, 0.083333336F)
    object FeetInch: LengthCoefficients(R.string.feet, R.string.inch, 12F)
    object MeterYard: LengthCoefficients(R.string.meter, R.string.yard, 1.0936133F)
    object YardMeter: LengthCoefficients(R.string.yard, R.string.meter, 0.9144F)
    object FeetYard: LengthCoefficients(R.string.feet, R.string.yard, 0.33333334F)
    object YardFeet: LengthCoefficients(R.string.yard, R.string.feet, 3F)
}
val lengthList = listOf(
    LengthCoefficients.KilometerMile,
    LengthCoefficients.MileKilometer,
    LengthCoefficients.NauticalMileKilometer,
    LengthCoefficients.KilometerNauticalMile,
    LengthCoefficients.MeterFeet,
    LengthCoefficients.FeetMeter,
    LengthCoefficients.InchCentimeter,
    LengthCoefficients.CentimeterInch,
    LengthCoefficients.InchMillimeter,
    LengthCoefficients.MillimeterInch,
    LengthCoefficients.InchFeet,
    LengthCoefficients.FeetInch,
    LengthCoefficients.MeterYard,
    LengthCoefficients.YardMeter,
    LengthCoefficients.FeetYard,
    LengthCoefficients.YardFeet
)