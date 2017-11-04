/*!
 * Copyright 2014 - David Sherret
 * MIT License
 */
 
 // Limits an input to specific values without checking keycodes and without flickering the value.
 //
 // Example usage:
 // $("input").limitRegex(/^[0-9]+\.?[0-9]{0,2}$/); -- limit to numbers, up to 2 decimal places
 //
 // http://jsfiddle.net/f1w38jw0/15/

(function($) { 
    $.fn.limitRegex = function(regex, onFail) {
        var pastValue, pastSelectionStart, pastSelectionEnd;
 
        $.each(this, function() {
            $(this).on("keydown", function() {
                pastValue          = this.value;
                pastSelectionStart = this.selectionStart;
                pastSelectionEnd   = this.selectionEnd;
            });

            $(this).on("input propertychange", function() {
                if (this.value.length > 0 && !regex.test(this.value)) {
                    if (typeof onFail === "function") {
                        onFail.call(this, this.value, pastValue);
                    }

                    this.value          = pastValue;
                    this.selectionStart = pastSelectionStart;
                    this.selectionEnd   = pastSelectionEnd;
                }
            });
        });

        return this;
    };
}(jQuery));