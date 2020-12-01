let ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "ajax/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    };
    makeEditable();
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/meals/", updateTableByData);
}