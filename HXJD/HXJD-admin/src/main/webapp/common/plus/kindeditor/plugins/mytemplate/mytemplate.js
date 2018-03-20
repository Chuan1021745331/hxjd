KindEditor.plugin('mytemplate', function(K) {
    var self = this, name = 'mytemplate', lang = self.lang(name + '.'),
        htmlPath = self.pluginsPath + name + '/html/';
    var serverUrl=bpath+"/admin/template/";
    function getFilePath(fileName) {
        return htmlPath + fileName + '?ver=' + encodeURIComponent(K.DEBUG ? K.TIME : K.VERSION);
    }
    self.clickToolbar(name, function() {
        //模板数组
        var templates=[];
        /*
         *拿到模板数据
         */
        $.ajax({
            url:bpath+'/admin/template/getAllTemplates',
            type:"get",
            dataType:"json",
            success:function (result) {
                console.log("result");
                console.log(result);
                templates=result.data;
                handleSuccessData(templates);
            },
            error:function (result) {

            }
        });
        function handleSuccessData(templates){
            var lang = self.lang(name + '.'),
                arr = ['<div style="padding:10px 20px;">',
                    '<div class="ke-header">',
                    // left start
                    '<div class="ke-left">',
                    ' 选择模板<select>'];
            K.each(templates, function(key, val) {
                arr.push('<option value="' + val.id + '">' + val.name + '</option>');
            });
            html = [arr.join(''),
                '</select></div>',
                // right start
                '<div class="ke-right">',
                '<input type="checkbox" id="keReplaceFlag" name="replaceFlag" value="1" /> <label for="keReplaceFlag">替换当前内容</label>',
                '</div>',
                '<div class="ke-clearfix"></div>',
                '</div>',
                '<iframe class="ke-textarea" frameborder="0" style="width:458px;height:260px;background-color:#FFF;"></iframe>',
                '</div>'].join('');
            var dialog = self.createDialog({
                name : name,
                width : 500,
                title : self.lang(name),
                body : html,
                yesBtn : {
                    name : self.lang('yes'),
                    click : function(e) {
                        var doc = K.iframeDoc(iframe);
                        self[checkbox[0].checked ? 'html' : 'insertHtml'](doc.body.innerHTML).hideDialog().focus();
                    }
                }
            });
            var selectBox = K('select', dialog.div),
                checkbox = K('[name="replaceFlag"]', dialog.div),
                iframe = K('iframe', dialog.div);
            checkbox[0].checked = true;
            iframe.attr('src', bpath+'/admin/template/getTemplate?id='+selectBox.val());
            selectBox.change(function() {
                iframe.attr('src', bpath+'/admin/template/getTemplate?id='+this.value);
            });
        }
    });

});
