/*! 
 * medium-editor-insert-plugin v0.1.1 - jQuery insert plugin for MediumEditor
 *
 * Addon Initialization
 *
 * https://github.com/orthes/medium-editor-images-plugin
 * 
 * Copyright (c) 2013 Pavel Linkesch (http://linkesch.sk)
 * Released under the MIT license
 */

(function ($) {
    
  /**
  * Extend MediumEditor's serialize function to get rid of unnecesarry Medium Editor Insert Plugin stuff
  * @return {object} content Object containing HTML content of each element
  */
  
  MediumEditor.prototype.serialize = function () {
    var i, j,
        elementid,
        content = {},
        $clone, $inserts, $insert, $insertData, html;
    for (i = 0; i < this.elements.length; i += 1) {
      elementid = (this.elements[i].id !== '') ? this.elements[i].id : 'element-' + i;
      
      $clone = $(this.elements[i]).clone();
      $inserts = $('.mediumInsert', $clone);
      for (j = 0; j < $inserts.length; j++) {
        $insert = $($inserts[j]);
        $insertData = $('.mediumInsert-placeholder', $insert).children();
        if ($insertData.length === 0) {
          $insert.remove();
        } else {
          $insert.removeAttr('contenteditable');
          $('img[draggable]', $insert).removeAttr('draggable');
          if ($insert.hasClass('small')) {
            $insertData.addClass('small');
          }
          $('.mediumInsert-buttons', $insert).remove();
          $insertData.unwrap();  
        }
      }
      
      html = $clone.html().trim();
      content[elementid] = {
        value: html
      };
    }
    return content;
  };
  
  
  
  /**
  * Medium Editor Insert Plugin
  * @param {object} options Options for the plugin
  * @param {void}
  */

  $.fn.mediumInsert = function (options) {
 
    $.fn.mediumInsert.settings = $.extend($.fn.mediumInsert.settings, options);

    
    /**
    * Initial plugin loop
    */
     
    return this.each(function () {

      $('p', this).bind('dragover drop', function (e) {
        e.preventDefault();
        return false;
      });
    
      $.fn.mediumInsert.insert.init($(this));
      
      if ($.fn.mediumInsert.settings.images === true) {
        $.fn.mediumInsert.images.init(); 
      }
      
      if ($.fn.mediumInsert.settings.maps === true) {
        $.fn.mediumInsert.maps.init(); 
      }
       
    });
 
  };
  
  
  /**
  * Settings
  */
  
  $.fn.mediumInsert.settings = {
    'imagesUploadScript': 'upload.php',
    'images': true,
    'maps': false
  };
  
  
  /**
  * Addon Initialization
  */
    
  $.fn.mediumInsert.insert = {
  
    /**
    * Insert initial function
    * @param {element} el Parent container element
    * @return {void}
    */
      
    init: function ($el) {
      this.$el = $el;
      this.setPlaceholders();  
    },
    
    /**
    * Deselect selected text
    * @return {void}
    */
    
    deselect: function () {
      document.getSelection().removeAllRanges();
    },
    
    /**
    * Method setting placeholders and basic events on them
    * @return {void}
    */
    
    setPlaceholders: function () {
      var that = this,
          $el = $.fn.mediumInsert.insert.$el,
          insertBlock = '',
          insertImage = '<a class="mediumInsert-action action-images-add">添加图片</a>',
          insertMap = '<a class="mediumInsert-action action-maps-add">Map</a>';
         
      if($.fn.mediumInsert.settings.images === true && $.fn.mediumInsert.settings.maps === true) {
        insertBlock = '<a class="mediumInsert-buttonsShow">Insert</a>'+
          '<ul class="mediumInsert-buttonsOptions">'+
            '<li>' + insertImage + '</li>' +
            '<li>' + insertMap + '</li>' +
          '</ul>';
      } else if ($.fn.mediumInsert.settings.images === true) {
        insertBlock = insertImage;
      } else if ($.fn.mediumInsert.settings.maps === true) {
        insertBlock = insertMap;
      }   
         
      if (insertBlock !== '') {
        insertBlock = '<div class="mediumInsert" contenteditable="false">'+
          '<div class="mediumInsert-buttons">'+
            '<div class="mediumInsert-buttonsIcon">&rarr;</div>'+
            insertBlock +
          '</div>'+
          '<div class="mediumInsert-placeholder"></div>'+
        '</div>';
      } else {
        return;
      }  
         
      if ($el.is(':empty')) {
        $el.html('<p><br></p>');
      }   
            
      $el.keyup(function () {        
        var i = 0;
      
        $el.children('p').each(function () {
          if ($(this).next().hasClass('mediumInsert') === false) {
            $(this).after(insertBlock);     
            $(this).next('.mediumInsert').attr('id', 'mediumInsert-'+ i);            
          }
          i++;
        });
      }).keyup(); 
        
      $el.on('selectstart', '.mediumInsert', function (e) {
        e.preventDefault();
        return false;
      });
      
      $el.on('blur', function () {
        var $clone = $(this).clone(),
            cloneHtml;
            
        $clone.find('.mediumInsert').remove();
        cloneHtml = $clone.html().replace(/^\s+|\s+$/g, '');

        if (cloneHtml === '' || cloneHtml === '<p><br></p>') {
          $(this).addClass('medium-editor-placeholder');  
        }
      });
  
        
      $el.on('click', '.mediumInsert-buttons a.mediumInsert-buttonsShow', function () {
        var $options = $(this).siblings('.mediumInsert-buttonsOptions'),
            $placeholder = $(this).parent().siblings('.mediumInsert-placeholder');

        if ($(this).hasClass('active')) {
          $(this).removeClass('active');
          $options.hide();
          
          $('a', $options).show();
        } else {
          $(this).addClass('active');
          $options.show();  
          
          $('a', $options).each(function () {
            var aClass = $(this).attr('class').split('action-')[1],
                plugin = aClass.split('-')[0];
            if ($('.mediumInsert-'+ plugin, $placeholder).length > 0) {
              $('a:not(.action-'+ aClass +')', $options).hide(); 
            }
          });
        }
          
        that.deselect();
      });
        
      $el.on('mouseleave', '.mediumInsert', function () {
        $('a.mediumInsert-buttonsShow', this).removeClass('active');
        $('.mediumInsert-buttonsOptions', this).hide();
      });
        
      $el.on('click', '.mediumInsert-buttons .mediumInsert-action', function () {
        var action = $(this).attr('class').split('action-')[1].split('-'),
            $placeholder = $(this).parents('.mediumInsert-buttons').siblings('.mediumInsert-placeholder');
        
        if ($.fn.mediumInsert[action[0]] && $.fn.mediumInsert[action[0]][action[1]]) {
          $.fn.mediumInsert[action[0]][action[1]]($placeholder);
        }
        
        $(this).parents('.mediumInsert').mouseleave();
      });
    } 
      
  };
 
}(jQuery));