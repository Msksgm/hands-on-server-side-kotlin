package list09_sealedModifier03

sealed class HttpStatusCode{
    class Success : HttpStatusCode()
    class ClientError(val code :Int) : HttpStatusCode()
    class ServerError(val code: Int):HttpStatusCode()
}

fun handleHttpStatusCode(code: Int) = when  (code){
    in 200..299 -> HttpStatusCode.Success()
    in 400..499 -> HttpStatusCode.ClientError(code)
    in 500..599 -> HttpStatusCode.ServerError(code)
    else -> throw IllegalArgumentException("Invalid HTTP status code")
}

fun main(args: Array<String>) {
    println(handleHttpStatusCode(200))
}
