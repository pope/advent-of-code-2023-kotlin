{
  description = "Advent of Code 2023 - Kotlin";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    nix-formatter-pack = {
      url = "github:Gerschtli/nix-formatter-pack";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs = { nixpkgs, nix-formatter-pack, ... }:
    let
      eachSystem = nixpkgs.lib.genAttrs [
        "aarch64-darwin"
        "x86_64-linux"
      ];
    in
    {
      devShells = eachSystem (system:
        let
          javaVersion = 20;
          overlays = [
            (_final: prev: rec {
              jdk = prev."jdk${toString javaVersion}";
              gradle = prev.gradle.override { java = jdk; };
              kotlin = prev.kotlin.override { jre = jdk; };
              kotlin-language-server = prev.kotlin-language-server.override {
                openjdk = jdk;
                inherit gradle;
              };
            })
          ];
          pkgs = import nixpkgs {
            inherit system overlays;
          };
        in
        with pkgs; {
          default = mkShell {
            packages = [
              gcc
              gradle
              kotlin
              kotlin-language-server
              ktlint
              ncurses
              patchelf
              zlib
            ];
          };
        }
      );
      formatter = eachSystem (system:
        nix-formatter-pack.lib.mkFormatter {
          pkgs = nixpkgs.legacyPackages.${system};
          config.tools = {
            deadnix.enable = true;
            nixpkgs-fmt.enable = true;
            statix.enable = true;
          };
        }
      );
    };
}
