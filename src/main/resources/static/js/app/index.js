/**
 * Functions used when registering a text
 * @type {{init: main.init, save: main.save}}
 */
const main = {
    init : function() {
        let _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
    },
    save : function() {
        let data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function() {
            alert('게시글이 등록 되었습니다.');
            window.location.href = '/';
        }).fail(function(err) {
            alert(JSON.stringify(err));
        })
    }
}; // end

main.init(); // initialize main function
