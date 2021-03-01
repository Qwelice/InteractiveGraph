package util

data class ConvertPlane (private var _width: Int, private var _height: Int, var xMin: Double, var xMax: Double, var yMin: Double, var yMax: Double){
    var width: Int
        get() = _width - 1
        set(value) {_width = value}
    var height: Int
        get() = _height - 1
        set(value) {_height = value}
}