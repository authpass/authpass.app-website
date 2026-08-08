#!/bin/bash

set -xeu

export DRY_RUN=${DRY_RUN:-}

dir="${0%/*}"
basedir="$dir/.."

cd $basedir

DC2F_ENV=production ./dc2f.sh build

if test -z "${DRY_RUN}" ; then
	./_tools/_deploy_web_sphene_net.sh

	./_tools/purge_cloudflare_cache.sh
fi


./_tools/gh-pages-deploy.sh


# Disabled: Google retired the sitemaps ping endpoint in 2023 and it now 404s,
# so `grep h2` matched nothing, returned non-zero, and `set -e` exited the
# script *after* the site had already deployed -- making every successful
# deploy look like a failure. Nothing replaces it: Google now reads the
# Sitemap: line in robots.txt and <lastmod> in sitemap.xml, both of which this
# site already publishes. (IndexNow would cover Bing/Yandex, but not Google.)
#if test -z "${DRY_RUN}" ; then
#	curl --silent --show-error http://www.google.com/webmasters/sitemaps/ping\?sitemap\=https://authpass.app/sitemap.xml | grep h2
#fi

