package com.makentoshe.sclient

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import space.jetbrains.api.runtime.SpaceHttpClient
import space.jetbrains.api.runtime.types.*
import space.jetbrains.api.runtime.withServiceAccountTokenSource

/**
 * Url of your Space instance.
 * This value may used for defining Space client using Client Credentials auth flow
 *
 * Example: https://instance.jetbrains.space
 */
private const val spaceInstanceUrl = "https:"

/**
 * Client for communication with Space. This implementation uses the Client Credentials auth flow.
 *
 * [spaceClient] is our API client – an instance of the SpaceHttpClientWithCallContext class.
 * WithCallContext stays here for a reason. Such a client allows working with the call context –
 * additional data that clarifies who made the call, how it was made, and so on.
 * We will use the context to get the ID of the user who made the request.
 *
 * SpaceHttpClient(HttpClient(CIO)).withServiceAccountTokenSource defines how the client will authenticate in Space:
 * withServiceAccountTokenSource is used for the Client Credentials Flow.
 * Other options would be withCallContext - for Refresh Token Flow and
 * withPermanentToken for Personal Token Authorization and other flows.
 *
 * HttpClient(CIO) here is a Ktor CIO HTTP client.
 */
val spaceClient = SpaceHttpClient(HttpClient(CIO)).withServiceAccountTokenSource(
    ClientCredentialsFlow.clientId, ClientCredentialsFlow.clientSecret, spaceInstanceUrl
)

/**
 * The Client Credentials Flow can be used by a server-side application that
 * accesses Space on behalf of itself, for example, a chatbot. The application
 * receives an access token from Space by sending it <i>client_id</i> and <i>client_secret</i>.
 *
 * Not all operations may be accessible using the Client Credentials Flow.
 * Many actions (for example, posting an article draft) require user consent
 * and cannot be performed with application credentials.
 *
 * For actions that should be performed on behalf of the user,
 * use other authorization flows, for example Resource Owner Password Credentials Flow.
 */
object ClientCredentialsFlow {

    const val clientId: String = TODO("Place your Space client id")

    const val clientSecret: String = TODO("Place your Space client secret")
}
