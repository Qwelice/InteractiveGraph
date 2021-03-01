package util

object Converter {
    fun xCrt2Scr(x: Double, plane: ConvertPlane) : Int{
        val px = plane.width / (plane.xMax - plane.xMin)
        return (x * px + (-plane.xMin) * px).toInt()
    }

    fun xScr2Crt(x: Int, plane: ConvertPlane) : Double{
        val px = plane.width / (plane.xMax - plane.xMin)
        return x * (1.0 / px) + plane.xMin
    }

    fun yCrt2Scr(y: Double, plane: ConvertPlane) : Int{
        val py = plane.height / (plane.yMax - plane.yMin)
        return (py * (plane.yMax - y)).toInt()
    }

    fun yScr2Crt(y: Int, plane: ConvertPlane) : Double{
        val py = plane.height / (plane.yMax - plane.yMin)
        return (-y) * (1.0 / py) + plane.yMax
    }
}