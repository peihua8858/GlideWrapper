package com.fz.imageloader.utils;

/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.net.URL;

/**
 * uri处理工具类
 *
 * @author dingpeihua
 * @version 1.0
 * @date 2019/1/14 09:20
 */
public class UriUtil {

    /**
     * http scheme for URIs
     */
    public static final String HTTP_SCHEME = "http";
    public static final String HTTPS_SCHEME = "https";

    /**
     * File scheme for URIs
     */
    public static final String LOCAL_FILE_SCHEME = "file";

    /**
     * Content URI scheme for URIs
     */
    public static final String LOCAL_CONTENT_SCHEME = "content";

    /**
     * URI prefix (including scheme) for contact photos
     */
    private static final Uri LOCAL_CONTACT_IMAGE_URI =
            Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo");

    /**
     * Asset scheme for URIs
     */
    public static final String LOCAL_ASSET_SCHEME = "asset";

    /**
     * Resource scheme for fully qualified resources which might have a package name that is different
     * than the application one. This has the constant value of "android.resource".
     */
    public static final String QUALIFIED_RESOURCE_SCHEME = ContentResolver.SCHEME_ANDROID_RESOURCE;

    /**
     * Data scheme for URIs
     */
    public static final String DATA_SCHEME = "data";

    /**
     * Convert android.net.Uri to java.net.URL as necessary for some networking APIs.
     *
     * @param uri uri to convert
     * @return load pointing to the same resource as uri
     */
    @Nullable
    public static URL uriToUrl(@Nullable Uri uri) {
        if (uri == null) {
            return null;
        }

        try {
            return new URL(uri.toString());
        } catch (java.net.MalformedURLException e) {
            // This should never happen since we got a valid uri
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if uri represents network resource
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "http" or "https"
     */
    public static boolean isNetworkUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return HTTPS_SCHEME.equals(scheme) || HTTP_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local file
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "file"
     */
    public static boolean isLocalFileUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_FILE_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local content
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "content"
     */
    public static boolean isLocalContentUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_CONTENT_SCHEME.equals(scheme);
    }

    /**
     * Checks if the given URI is a general Contact URI, and not a specific display photo.
     *
     * @param uri the URI to check
     * @return true if the uri is a Contact URI, and is not already specifying a display photo.
     */
    public static boolean isLocalContactUri(Uri uri) {
        return isLocalContentUri(uri)
                && ContactsContract.AUTHORITY.equals(uri.getAuthority())
                && !uri.getPath().startsWith(LOCAL_CONTACT_IMAGE_URI.getPath());
    }

    /**
     * Checks if the given URI is for a photo from the device's local media store.
     *
     * @param uri the URI to check
     * @return true if the URI points to a media store photo
     */
    public static boolean isLocalCameraUri(Uri uri) {
        String uriString = uri.toString();
        return uriString.startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())
                || uriString.startsWith(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString());
    }

    /**
     * Check if uri represents local asset
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "asset"
     */
    public static boolean isLocalAssetUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_ASSET_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents fully qualified resource URI.
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to {@link #QUALIFIED_RESOURCE_SCHEME}
     */
    public static boolean isQualifiedResourceUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return QUALIFIED_RESOURCE_SCHEME.equals(scheme);
    }

    /**
     * Check if the uri is a data uri
     */
    public static boolean isDataUri(@Nullable Uri uri) {
        return DATA_SCHEME.equals(getSchemeOrNull(uri));
    }

    /**
     * @param uri uri to extract scheme from, possibly null
     * @return null if uri is null, result of uri.getScheme() otherwise
     */
    @Nullable
    public static String getSchemeOrNull(@Nullable Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    /**
     * A wrapper around {@link Uri#parse} that returns null if the input is null.
     *
     * @param uriAsString the uri as a string
     * @return the parsed Uri or null if the input was null
     */
    public static Uri parseUriOrNull(@Nullable String uriAsString) {
        return uriAsString != null ? Uri.parse(uriAsString) : null;
    }

    /**
     * Get the path of a file from the Uri.
     *
     * @param contentResolver the content resolver which will query for the source file
     * @param srcUri          The source uri
     * @return The Path for the file or null if doesn't exists
     */
    @Nullable
    public static String getRealPathFromUri(ContentResolver contentResolver, final Uri srcUri) {
        String result = null;
        if (isLocalContentUri(srcUri)) {
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(srcUri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (idx != -1) {
                        result = cursor.getString(idx);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (isLocalFileUri(srcUri)) {
            result = srcUri.getPath();
        }
        return result;
    }

    /**
     * Returns a URI for a given file using {@link Uri#fromFile(File)}.
     *
     * @param file a file with a valid path
     * @return the URI
     */
    public static Uri getUriForFile(File file) {
        return Uri.fromFile(file);
    }

    /**
     * Returns a URI for the given resource ID in the given package. Use this method only if you need
     * to specify a package name different to your application's main package.
     *
     * @param packageName a package name (e.g. com.facebook.myapp.plugin)
     * @param resourceId  to resource ID to use
     * @return the URI
     */
    public static Uri getUriForQualifiedResource(String packageName, int resourceId) {
        return new Uri.Builder()
                .scheme(QUALIFIED_RESOURCE_SCHEME)
                .authority(packageName)
                .path(String.valueOf(resourceId))
                .build();
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    public static Uri getResourceUri(Context context, Integer model) {
        try {
            Resources resources = context.getResources();
            return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(model) + '/'
                    + resources.getResourceTypeName(model) + '/'
                    + resources.getResourceEntryName(model));
        } catch (Resources.NotFoundException e) {
            Log.w("UriUtil", "Received invalid resource id: " + model, e);
            return null;
        }
    }
}
