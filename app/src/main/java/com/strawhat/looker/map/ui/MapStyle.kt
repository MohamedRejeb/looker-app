package com.strawhat.looker.map.ui

object MapStyle {
    val json = """
        [
            {
                "featureType": "administrative",
                "elementType": "all",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "lightness": -100
                    },
                    {
                        "visibility": "off"
                    },
                    {
                        "saturation": "-77"
                    }
                ]
            },
            {
                "featureType": "administrative",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "visibility": "on"
                    },
                    {
                        "color": "#848ea4"
                    }
                ]
            },
            {
                "featureType": "landscape",
                "elementType": "geometry",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "saturation": "-70"
                    },
                    {
                        "lightness": "0"
                    },
                    {
                        "visibility": "on"
                    }
                ]
            },
            {
                "featureType": "landscape",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "hue": "#0050ff"
                    },
                    {
                        "saturation": "0"
                    },
                    {
                        "lightness": "0"
                    }
                ]
            },
            {
                "featureType": "landscape",
                "elementType": "labels",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "saturation": "-80"
                    },
                    {
                        "lightness": "-90"
                    },
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "all",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "saturation": "-80"
                    },
                    {
                        "lightness": "-70"
                    },
                    {
                        "visibility": "off"
                    },
                    {
                        "gamma": "1"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "geometry",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "saturation": "-85"
                    },
                    {
                        "lightness": "60"
                    },
                    {
                        "visibility": "on"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "labels",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "saturation": "-70"
                    },
                    {
                        "lightness": "50"
                    },
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "road.local",
                "elementType": "all",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "saturation": "0"
                    },
                    {
                        "lightness": "-11"
                    },
                    {
                        "visibility": "on"
                    }
                ]
            },
            {
                "featureType": "transit",
                "elementType": "geometry",
                "stylers": [
                    {
                        "visibility": "simplified"
                    },
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "lightness": "0"
                    },
                    {
                        "saturation": "0"
                    }
                ]
            },
            {
                "featureType": "transit",
                "elementType": "labels",
                "stylers": [
                    {
                        "hue": "#0060ff"
                    },
                    {
                        "lightness": -100
                    },
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [
                    {
                        "hue": "#0066ff"
                    },
                    {
                        "saturation": "0"
                    },
                    {
                        "lightness": 100
                    },
                    {
                        "visibility": "on"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "labels",
                "stylers": [
                    {
                        "hue": "#000000"
                    },
                    {
                        "saturation": -100
                    },
                    {
                        "lightness": -100
                    },
                    {
                        "visibility": "off"
                    }
                ]
            }
        ] 
    """.trimIndent()
}