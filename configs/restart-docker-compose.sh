for i in $(seq 0 5) ; do
  rm -rf ./800"$i"/data;
done;

# shellcheck disable=SC2046
docker stop $(docker ps -a -q) && docker rm $(docker ps -a -q);
docker-compose up -d