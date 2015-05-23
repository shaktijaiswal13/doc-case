// Mock an AJAX request
$.mockjax({
    url: './rest/documents',
    response: function() {
        this.responseText = [{
            "id": "5557bba230049ab27c8d0d00",
            "name": "Electricity bill",
            "scanned": "true",
            "url": "/rest/file/5557bba230049ab27c8d0cff",
            "coloured": "true",
            "signed": "true",
            "description": "November electricity bill",
            "type": null,
            "label": null
        }, {
            "id": "5557bda33004155aac98efcf",
            "name": "Electricity bill1",
            "scanned": "true",
            "url": "/rest/file/5557bda33004155aac98efce",
            "coloured": "true",
            "signed": "true",
            "description": "November electricity bill",
            "type": null,
            "label": null
        }, {
            "id": "5557bee43004155aac98efd1",
            "name": "Shiv Parivaar",
            "scanned": "true",
            "url": "/rest/file/5557bee43004155aac98efd0",
            "coloured": "true",
            "signed": "true",
            "description": "Mahadev Parvati Image",
            "type": null,
            "label": null
        }];
    }
});

$.mockjax({
    url: './rest/labels',
    response: function() {
        this.responseText = ["Amsterdam",
            "London",
            "Paris",
            "Washington",
            "New York",
            "Los Angeles",
            "Sydney",
            "Melbourne",
            "Canberra",
            "Beijing",
            "New Delhi",
            "Kathmandu",
            "Cairo",
            "Cape Town",
            "Kinshasa"
        ];
    }
});