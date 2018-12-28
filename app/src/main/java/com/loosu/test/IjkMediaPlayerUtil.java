package com.loosu.test;


import android.content.Context;

import com.loosu.soplayer.R;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class IjkMediaPlayerUtil {
    public static String infoToString(int info) {
        switch (info) {
            case IMediaPlayer.MEDIA_INFO_UNKNOWN:               // 1
                return "MEDIA_INFO_UNKNOWN";
            case IMediaPlayer.MEDIA_INFO_STARTED_AS_NEXT:       // 2
                return "MEDIA_INFO_STARTED_AS_NEXT";
            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: // 3
                return "MEDIA_INFO_VIDEO_RENDERING_START";
            case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:   // 700
                return "MEDIA_INFO_VIDEO_TRACK_LAGGING";
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:       // 701
                return "MEDIA_INFO_BUFFERING_START";
            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:         // 702
                return "MEDIA_INFO_BUFFERING_END";
            case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:     // 703
                return "MEDIA_INFO_NETWORK_BANDWIDTH";
            case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:      // 800
                return "MEDIA_INFO_BAD_INTERLEAVING";
            case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:          // 801
                return "MEDIA_INFO_NOT_SEEKABLE";
            case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:       // 802
                return "MEDIA_INFO_METADATA_UPDATE";
            case IMediaPlayer.MEDIA_INFO_TIMED_TEXT_ERROR:      // 900
                return "MEDIA_INFO_TIMED_TEXT_ERROR";
            case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:  // 901
                return "MEDIA_INFO_UNSUPPORTED_SUBTITLE";
            case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:    // 902
                return "MEDIA_INFO_SUBTITLE_TIMED_OUT";
            case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:        // 10001
                return "MEDIA_INFO_VIDEO_ROTATION_CHANGED";
            case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:         // 10002
                return "MEDIA_INFO_AUDIO_RENDERING_START";
            case IMediaPlayer.MEDIA_INFO_AUDIO_DECODED_START:           // 10003
                return "MEDIA_INFO_AUDIO_DECODED_START";
            case IMediaPlayer.MEDIA_INFO_VIDEO_DECODED_START:           // 10004
                return "MEDIA_INFO_VIDEO_DECODED_START";
            case IMediaPlayer.MEDIA_INFO_OPEN_INPUT:                    // 10005
                return "MEDIA_INFO_OPEN_INPUT";
            case IMediaPlayer.MEDIA_INFO_FIND_STREAM_INFO:              // 10006
                return "MEDIA_INFO_FIND_STREAM_INFO";
            case IMediaPlayer.MEDIA_INFO_COMPONENT_OPEN:                // 10007
                return "MEDIA_INFO_COMPONENT_OPEN";
            case IMediaPlayer.MEDIA_INFO_VIDEO_SEEK_RENDERING_START:    // 10008
                return "MEDIA_INFO_VIDEO_SEEK_RENDERING_START";
            case IMediaPlayer.MEDIA_INFO_AUDIO_SEEK_RENDERING_START:    // 10009
                return "MEDIA_INFO_AUDIO_SEEK_RENDERING_START";
            case IMediaPlayer.MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE:  // 10100
                return "MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE";
            default:
                return String.valueOf(info);
        }
    }

    public static String infoToString(Context context, int info) {
        switch (info) {
            case IMediaPlayer.MEDIA_INFO_UNKNOWN:               // 1
                return context.getString(R.string.media_info_unknown);

            case IMediaPlayer.MEDIA_INFO_STARTED_AS_NEXT:       // 2
                return context.getString(R.string.media_info_started_as_next);

            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: // 3
                return context.getString(R.string.media_info_video_rendering_start);

            case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:   // 700
                return context.getString(R.string.media_info_video_track_lagging);

            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:       // 701
                return context.getString(R.string.media_info_buffering_start);

            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:         // 702
                return context.getString(R.string.media_info_buffering_end);

            case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:     // 703
                return context.getString(R.string.media_info_network_bandwidth);

            case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:      // 800
                return context.getString(R.string.media_info_bad_interleaving);

            case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:          // 801
                return context.getString(R.string.media_info_not_seekable);

            case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:       // 802
                return context.getString(R.string.media_info_metadata_update);

            case IMediaPlayer.MEDIA_INFO_TIMED_TEXT_ERROR:      // 900
                return context.getString(R.string.media_info_timed_text_error);

            case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:  // 901
                return context.getString(R.string.media_info_unsupported_subtitle);

            case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:    // 902
                return context.getString(R.string.media_info_subtitle_timed_out);

            case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:        // 10001
                return context.getString(R.string.media_info_video_rotation_changed);

            case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:         // 10002
                return context.getString(R.string.media_info_audio_rendering_start);

            case IMediaPlayer.MEDIA_INFO_AUDIO_DECODED_START:           // 10003
                return context.getString(R.string.media_info_audio_decoded_start);

            case IMediaPlayer.MEDIA_INFO_VIDEO_DECODED_START:           // 10004
                return context.getString(R.string.media_info_video_decoded_start);

            case IMediaPlayer.MEDIA_INFO_OPEN_INPUT:                    // 10005
                return context.getString(R.string.media_info_open_input);

            case IMediaPlayer.MEDIA_INFO_FIND_STREAM_INFO:              // 10006
                return context.getString(R.string.media_info_find_stream_info);

            case IMediaPlayer.MEDIA_INFO_COMPONENT_OPEN:                // 10007
                return context.getString(R.string.media_info_component_open);

            case IMediaPlayer.MEDIA_INFO_VIDEO_SEEK_RENDERING_START:    // 10008
                return context.getString(R.string.media_info_video_seek_rendering_start);

            case IMediaPlayer.MEDIA_INFO_AUDIO_SEEK_RENDERING_START:    // 10009
                return context.getString(R.string.media_info_audio_rendering_start);

            case IMediaPlayer.MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE:  // 10100
                return context.getString(R.string.media_info_media_accurate_seek_complete);

            default:
                return String.valueOf(info);
        }
    }

    public static String errorToString(int error) {
        switch (error) {
            case IMediaPlayer.MEDIA_ERROR_UNKNOWN:      // 1
                return "MEDIA_ERROR_UNKNOWN";
            case IMediaPlayer.MEDIA_ERROR_SERVER_DIED:  // 100
                return "MEDIA_ERROR_SERVER_DIED";
            case IMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:   // 200
                return "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK";
            case IMediaPlayer.MEDIA_ERROR_IO:           // -1004
                return "MEDIA_ERROR_IO";
            case IMediaPlayer.MEDIA_ERROR_MALFORMED:    // -1007
                return "MEDIA_ERROR_MALFORMED";
            case IMediaPlayer.MEDIA_ERROR_UNSUPPORTED:  // -1010
                return "MEDIA_ERROR_UNSUPPORTED";
            case IMediaPlayer.MEDIA_ERROR_TIMED_OUT:    // -110
                return "MEDIA_ERROR_TIMED_OUT";
            default:
                return String.valueOf(error);
        }
    }

    public static String errorToString(Context context, int error) {
        switch (error) {
            case IMediaPlayer.MEDIA_ERROR_UNKNOWN:      // 1
                return context.getString(R.string.media_error_unknown);

            case IMediaPlayer.MEDIA_ERROR_SERVER_DIED:  // 100
                return context.getString(R.string.media_error_server_died);

            case IMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:   // 200
                return context.getString(R.string.media_error_not_valid_for_progressive_playback);

            case IMediaPlayer.MEDIA_ERROR_IO:           // -1004
                return context.getString(R.string.media_error_io);

            case IMediaPlayer.MEDIA_ERROR_MALFORMED:    // -1007
                return context.getString(R.string.media_error_malformed);

            case IMediaPlayer.MEDIA_ERROR_UNSUPPORTED:  // -1010
                return context.getString(R.string.media_error_unsupported);

            case IMediaPlayer.MEDIA_ERROR_TIMED_OUT:    // -110
                return context.getString(R.string.media_error_timed_out);

            default:
                return String.valueOf(error);
        }
    }
}