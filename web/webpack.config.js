import { resolve } from 'node:path'
import { VueLoaderPlugin } from 'vue-loader'
import HtmlWebpackPlugin from 'html-webpack-plugin'
import CopyPlugin from 'copy-webpack-plugin'

const RE_RAW = /raw/
const RE_NON_RAW = /^((?!raw).)*$/

const config = {
  mode: 'development',
  entry: {
    main: resolve(import.meta.dirname, 'main.js'),
  },
  module: {
    rules: [
      {
        resourceQuery: RE_RAW,
        type: 'asset/source',
      },
      {
        resourceQuery: RE_NON_RAW,
        test: /\.vue$/,
        use: 'vue-loader',
      },
      {
        resourceQuery: RE_NON_RAW,
        test: /.css$/,
        use: ['style-loader', 'css-loader'],
      },
      {
        resourceQuery: RE_NON_RAW,
        test: /.less$/,
        use: ['style-loader', 'css-loader', 'less-loader'],
      },
      {
        resourceQuery: RE_NON_RAW,
        test: /\.[jt]sx?$/i,
        use: [
          {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env', ['@babel/preset-typescript', { isTSX: true, allExtensions: true }]],
              plugins: ['@vue/babel-plugin-jsx'],
            },
          },
        ],
        exclude: /node_modules/,
      },
      {
        resourceQuery: RE_NON_RAW,
        test: /\.ya?ml$/,
        use: 'yaml-loader',
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: resolve(import.meta.dirname, 'index.html'),
    }),
    new VueLoaderPlugin(),
    new CopyPlugin({
      patterns: [
        {
          from: resolve(import.meta.dirname, 'public'),
          to: resolve(import.meta.dirname, 'dist'),
        },
      ],
    }),
  ],
  output: {
    iife: true,
    filename: '[name].[chunkhash].js',
  },
  performance: {
    maxEntrypointSize: 1024 * 1024,
    maxAssetSize: 1024 * 1024,
  },
  devServer: {
    headers: {
      'Access-Control-Allow-Origin': '*',
    },
    allowedHosts: 'all',
    host: 'localhost',
    liveReload: true,
    hot: true,
  },
}

export default function defineConfig(env) {
  return config
}
