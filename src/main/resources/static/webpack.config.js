const path = require("path");
module.exports = {
    mode: "development",
    entry: "./ts/index.ts",
    devtool: "inline-source-map",
    module: {
        rules: [
            {
                use: "ts-loader",
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: [".ts", ".js"],
    },
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "dist"),
    },
}