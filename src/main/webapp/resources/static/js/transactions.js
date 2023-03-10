$("#show-form").click(function () {
    $(this).toggle("hidden");
    $("#add-transaction-form").toggle("hidden");
});

$("#cancel-button").click(function () {
    $("#add-transaction-form").trigger("reset");
    $("#show-form").toggle("hidden");
    $("#add-transaction-form").toggle("hidden");
});