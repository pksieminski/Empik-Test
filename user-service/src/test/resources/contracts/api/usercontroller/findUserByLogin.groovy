package contracts.api.usercontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return user by login=pksieminski"

    request {
        url "/users/pksieminski"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                id: 57523979,
                login: "pksieminski",
                name: null,
                type: "User",
                avatarUrl: "https://avatars.githubusercontent.com/u/57523979?v=4",
                createdAt: "2019-11-08T09:09:47Z",
                calculations: 0.0
        )
    }
}