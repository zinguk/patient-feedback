if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(
                function() {
                    $(this).fadeIn();
                }).ajaxStop(function() {
                    $(this).fadeOut();
                });
    })(jQuery);
}

function updateSelect(records, rselect, callback) { // The response comes back as a bunch-o-JSON
    if (records) {
        rselect.empty();

        // Rebuild the select
        for (var i = 0; i < records.length; i++) {
            var record = records[i];
            rselect.append($("<option></option>").attr("value", record.value).text(record.text));
        }
    }

    if (callback != null) {
        callback();
    }
}

function showOrHide(objects, show) {
    objects.each(function (index) {
        if (show) {
            objects[index].style.display = '';
        } else {
            objects[index].style.display = 'none';
        }
    });
}

function handleError(xhr) {
    alert('Error!');
}