package com.zkp.breath.adpter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zkp.breath.R


class GlideAdapter(data: MutableList<String>? = null) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.adpter_glide, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        val imageView = holder.getView<ImageView>(R.id.iv)

        val sharedOptions: RequestOptions = RequestOptions()
                .placeholder(R.drawable.block_canary_icon)
                .error(R.drawable.block_canary_icon)
                // 数据为null也视为合法情况
                .fallback(R.drawable.block_canary_icon)
                // 默认磁盘缓存策略
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                // 是否跳过内存缓存
                .skipMemoryCache(false)
                // 变化策略
                .centerCrop()
                // 多重变化策略
                .transform(CenterCrop(), GranularRoundedCorners(100f, 100f, 0f, 0f))
                //
                .override(150, 300)

        Glide.with(imageView.context)
                .load(item)
                /**
                 * 缩略图加载，其实就是先加载显示一张低分辨率的图片，再显示高分辨率，提供更好的视觉过度效果，
                 * 和主请求并行启动，如果缩略图先加载完毕则先显示，如果后加载完毕则不显示。
                 *
                 * 如果有提供缩略图的url则使用如下 ：Glide.with(context)load(thumbnailUrl)，但是一般
                 * 的公司不会切多套图；所以还可以使用目标url但指定宽高：Glide.with(imageView.context).load(item).override(500, 500)，
                 * 还有一个简化版本只需要sizeMultiplier参数，指定尺寸为原来尺寸的百分比。
                 *
                 */
                .thumbnail(0.25f)
                // 交叉淡入变换
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(sharedOptions)
                .into(imageView)
    }

}