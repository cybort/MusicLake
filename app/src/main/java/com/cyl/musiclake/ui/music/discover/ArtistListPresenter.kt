package com.cyl.musiclake.ui.music.discover

import com.cyl.musicapi.bean.Artists
import com.cyl.musiclake.api.MusicApiServiceImpl
import com.cyl.musiclake.base.BasePresenter
import com.cyl.musiclake.bean.Artist
import com.cyl.musiclake.common.Constants
import com.cyl.musiclake.net.ApiManager
import com.cyl.musiclake.net.RequestCallBack
import com.cyl.musiclake.utils.ToastUtils
import javax.inject.Inject

/**
 * Des    :
 * Author : master.
 * Date   : 2018/5/20 .
 */
class ArtistListPresenter @Inject
constructor() : BasePresenter<ArtistListContract.View>(), ArtistListContract.Presenter {
    override fun loadArtists(offset: Int, params: Any) {
        mView?.showLoading()
        ApiManager.request(MusicApiServiceImpl.getArtists(offset, params), object : RequestCallBack<Artists> {
            override fun success(result: Artists) {
                val artists = mutableListOf<Artist>()
                result.singerList.forEach {
                    val artist = Artist().apply {
                        name = it.singer_name
                        artistId = it.singer_id
                        picUrl = it.singer_pic
                        type = Constants.QQ
                    }
                    artists.add(artist)
                }
                mView?.hideLoading()
                mView?.showArtistList(artists)
                mView?.showArtistTags(result.tags)
            }

            override fun error(msg: String) {
                mView?.hideLoading()
                mView?.showError(msg,true)
            }
        })
    }

}
