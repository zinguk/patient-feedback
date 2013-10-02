class UrlMappings {
	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'home', action: 'index')
		"404"(view: '/error404')
		"400"(view:'/error')
		"500"(view:'/error')
	}
}
