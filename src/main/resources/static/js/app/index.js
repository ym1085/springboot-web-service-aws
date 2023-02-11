/**
 * Functions used when registering a text
 * @type {{init: main.init, save: main.save}}
 */
const main = {
    init: function () {
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save: function () {
        let data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
        })
            .done(function () {
                alert('게시글이 등록 되었습니다.');
                window.location.href = '/';
            })
            .fail(function (err) {
                alert(JSON.stringify(err));
            });
    },
    update: function () {
        const id = $('#id').val();
        let data = {
            title: $('#title').val(),
            content: $('#content').val(),
        };
        console.log(`data => ${JSON.stringify(data)}`);
        console.log(`id => ${id}`);
        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
        })
            .done(function (response) {
                console.debug(`response => ${JSON.stringify(response)}`);
                alert(`게시글이 수정되었습니다.`);
                location.href = '/';
            })
            .fail(function (err) {
                alert(`error => ${JSON.stringify(err)}`);
            });
    },
    delete: function () {
        const id = $('#id').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dateType: 'json',
            contentType: 'application/json; charset=utf-8',
        })
            .done(function () {
                alert('글이 삭제되었습니다.');
                location.href = '/';
            })
            .fail(function (err) {
                alert(`error => ${JSON.stringify(err)}`);
                alert(JSON.stringify(err));
            });
    },
}; // end

main.init(); // initialize main function
