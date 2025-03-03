const path = require("path");
module.exports = {
    entry: "./src/index.ts",
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
        // require('./example')과 같이 빈 확장자를 아래 확장자로 import 해줌.
        extensions: [".ts"],
    },
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "dist"),
    },
}