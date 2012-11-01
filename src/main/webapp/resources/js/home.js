$(document).ready(function () {
    $("#tags").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "rs/data/objects/name/" + request.term + "?maxResults=10",
                dataType: "json",
                type: "GET",
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.name,
                            value: item.name
                        };
                    }));
                },
                error: function (a, b, c) {
                    debugger;
                }
            });
        },
        select: function(event, ui) {
            if (ui.item) {
                $(this).val(ui.item.value);
            }
        	$("#go").trigger("click");
        },
        minLength: 1
    });
    $("#go").click(function() {
        if (tags.value != '') {
            location.href = "rs/data/object/" + tags.value;
        }
    });
});
