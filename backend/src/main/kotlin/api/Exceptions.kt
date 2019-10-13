package revolut.backend.api

import revolut.backend.BackendException

abstract class ApiException(message: String, cause: Throwable? = null) : BackendException(message, cause)

class DeploymentFailedException(cause: Exception) : ApiException("Deployment failed!", cause)