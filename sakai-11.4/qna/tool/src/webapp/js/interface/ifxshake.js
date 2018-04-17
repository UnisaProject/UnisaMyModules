/*
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @name Shake
 * @description makes the element to shake
 * @param Integer times how many tomes to shake the element
 * @param Function callback (optional) A function to be executed whenever the animation completes.
 * @type jQuery
 * @cat Plugins/Interface
 * @author Stefan Petre
 */
jQuery.fn.Shake = function (times, callback) {
	return this.queue('interfaceFX',function(){
		if (!jQuery.fxCheckTag(this)) {
			jQuery.dequeue(this, 'interfaceFX');
			return false;
		}
		var e = new jQuery.fx.Shake(this, times, callback);
		e.shake();
	});
};
jQuery.fx.Shake = function (e, times, callback)
{
	var z = this;
	z.el = jQuery(e);
	z.el.show();
	z.times = parseInt(times)||3;
	z.callback = callback;
	z.cnt = 1;
	z.oldStyle = {};
	z.oldStyle.position = z.el.css('position');
	z.oldStyle.top = parseInt(z.el.css('top'))||0;
	z.oldStyle.left = parseInt(z.el.css('left'))||0;
	
	if (z.oldStyle.position != 'relative' && z.oldStyle.position != 'absolute') {
		z.el.css('position', 'relative');
	}
	
	z.shake = function ()
	{
		z.cnt ++;
		
		z.e = new jQuery.fx(
			z.el.get(0), 
			{
				duration: 60,
				complete : function ()
				{
					z.e = new jQuery.fx(
						z.el.get(0), 
						 {
							 duration: 60,
							 complete : function ()
							 {
								z.e = new jQuery.fx(
									e,
									{
										duration: 60, 
										complete: function(){
											if (z.cnt <= z.times)
												z.shake();
											else {
												z.el.css('position', z.oldStyle.position).css('top', z.oldStyle.top + 'px').css('left', z.oldStyle.left + 'px');
												jQuery.dequeue(z.el.get(0), 'interfaceFX');
												if (z.callback && z.callback.constructor == Function) {
													z.callback.apply(z.el.get(0));
												}
											}
										}
									},
									'left'
								);
								z.e.custom (z.oldStyle.left-20, z.oldStyle.left);
							 }
						},
						'left'
					);
					z.e.custom (z.oldStyle.left+20, z.oldStyle.left-20);
				}
			},
			'left'
		);
		z.e.custom (z.oldStyle.left, z.oldStyle.left+20);
	};
		
};