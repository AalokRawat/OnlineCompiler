'use strict';

var result = document.querySelector('#result');

$(document).ready(function () {

    $("#submitButton").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        // Get form
        var form = $('#editorForm')[0];

        // Create an FormData object
        var data = new FormData(form);

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/compiler/java",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                result.innerHTML = data ;

            },
            error: function (e) {
                result.innerHTML = "Error : "+e;
            }
        });

    });

});